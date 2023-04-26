package com.yhao.webdemo.common.distLock;

import com.yhao.webdemo.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class RedisLockAspect {

    private ConcurrentLinkedDeque<RedisLockDefinitionHolder> taskList;

    @PostConstruct
    private void init() {
        log.info("添加分布式锁看门狗");
        startWatchDog();
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.yhao.webdemo.common.distLock.RedisLock)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object withDistLock(ProceedingJoinPoint pjp) throws Throwable {
        Method method = AopUtil.resolveMethod(pjp);
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        String businessCode = annotation.lockType().getCode();
        String uuid = UUID.randomUUID().toString();
        log.info("任务尝试获取分布式锁");
        Object result = null;
        try {
            if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(businessCode, uuid))) {
                throw new DistLockException("锁被占用，无法加锁");
            }
            redisTemplate.expire(businessCode, annotation.lockTime(), TimeUnit.SECONDS);
            Thread currentThread = Thread.currentThread();
            taskList.add(new RedisLockDefinitionHolder(annotation, currentThread));
            result = pjp.proceed();
            if (currentThread.isInterrupted()) {
                throw new InterruptedException("线程被打断");
            }
        } catch (DistLockException e) {
            log.info(e.getMessage());
        } catch (Throwable e) {
            log.info("任务执行失败，请回滚");
            throw e;
        } finally {
            redisTemplate.delete(businessCode);
            log.info("执行完毕，释放锁，业务代码：" + businessCode);
        }
        return result;
    }

    private void startWatchDog() {
        taskList = new ConcurrentLinkedDeque<>();
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("redisLock-schedule-pool").daemon(true)
                .build();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory);
        Runnable task = () -> {
            Iterator<RedisLockDefinitionHolder> itr = taskList.iterator();
            while (itr.hasNext()) {
                RedisLockDefinitionHolder taskHolder = itr.next();
                if (taskHolder == null) {
                    itr.remove();
                    continue;
                }
                if (redisTemplate.opsForValue().get(taskHolder.getBusinessKey()) == null) {
                    itr.remove();
                    continue;
                }
                if (taskHolder.getCurrentCount() > taskHolder.getTryCount()) {
                    taskHolder.getCurrentThread().interrupt();
                    itr.remove();
                    continue;
                }
                long currentTime = System.currentTimeMillis();
                boolean shouldExtend = (taskHolder.getLastModifyTime() + taskHolder.getModifyPeriod()) <= currentTime;
                if (shouldExtend) {
                    taskHolder.setLastModifyTime(currentTime);
                    redisTemplate.expire(taskHolder.getBusinessKey(), taskHolder.getLastModifyTime(), TimeUnit.SECONDS);
                    log.info("业务：[{}] 已经申请 {} 次分布式锁了", taskHolder.getBusinessKey(), taskHolder.getCurrentCount());
                    taskHolder.setCurrentCount(taskHolder.getCurrentCount() + 1);
                }
            }
        };
        log.info("开始定期检查分布式锁的状态");
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }

}
