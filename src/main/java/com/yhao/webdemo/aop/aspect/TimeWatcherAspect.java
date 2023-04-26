package com.yhao.webdemo.aop.aspect;

import com.yhao.webdemo.aop.annotation.TimeWatcher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class TimeWatcherAspect {

    @Pointcut("@annotation(com.yhao.webdemo.aop.annotation.TimeWatcher)")
    public void timeWatcher() {
    }

    ;

    @Around("timeWatcher()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        Class<?> clazz = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = clazz.getMethod(signature.getName(), signature.getParameterTypes());
        String dscp = method.getAnnotation(TimeWatcher.class).dscp();
        System.out.printf("time of %s: %d ms\n%n", dscp, endTime - startTime);
        return result;
    }
}
