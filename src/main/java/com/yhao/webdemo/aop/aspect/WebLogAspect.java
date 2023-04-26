package com.yhao.webdemo.aop.aspect;

import com.yhao.webdemo.aop.annotation.WebLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("@annotation(com.yhao.webdemo.aop.annotation.WebLog)")
    public void webLog() {
    }

    @Before("webLog()")
    public void before(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("============= before =============");
        String info = getWebLogInfo(joinPoint);
        logger.info("Point info: {}", info);
        logger.info("URL: {}", request.getRequestURL().toString());
        logger.info("HTTP Method: {}", request.getMethod());
        logger.info("Class Method: {}::{}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("IP: {}", request.getRemoteAddr());
        logger.info("Args: {}", Arrays.asList(joinPoint.getArgs()));
    }

    @After("webLog()")
    public void after() {
        logger.info("============= after =============");
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("============= doAround =============");
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        logger.info("Output args: {}", result);
        logger.info("Execution time: {}", System.currentTimeMillis() - startTime);
        return result;
    }

    @AfterReturning("webLog()")
    public void afterReturning() throws Throwable {
        logger.info("============ afterReturning ==========");
    }

    @AfterThrowing("webLog()")
    public void afterThrowing() throws Throwable {
        logger.info("============ afterThrowing ==========");
    }

    // 获取web日志注解信息
    private String getWebLogInfo(JoinPoint joinPoint) throws ClassNotFoundException {
        // 获取切点的目标类
        String targetName = joinPoint.getTarget().getClass().getName();
        Class<?> targetClass = Class.forName(targetName);
        // 获取切点的目标方法名
        String targetMethod = joinPoint.getSignature().getName();
        // 获取切点方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取目标类的所有方法
        Method[] methods = targetClass.getMethods();
        // 方法名相同，包含目标注解，方法参数个数相同（避免重载）
        for (Method method : methods) {
            if (method.getName().equals(targetMethod)
                    && method.isAnnotationPresent(WebLog.class)
                    && method.getParameterTypes().length == args.length) {
                return method.getAnnotation(WebLog.class).value();
            }
        }
        return "";
    }
}

