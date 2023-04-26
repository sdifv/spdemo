package com.yhao.webdemo.dao.aop;

import com.yhao.webdemo.dao.dynamic.DataSourceContextHolder;
import com.yhao.webdemo.dao.dynamic.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(value = 1)
@Slf4j
public class DataSourceSwitchAspect {


    @Around("@annotation(com.yhao.webdemo.dao.aop.DataSourceSwitcher)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = false;
        try {
            Method method = this.getMethod(pjp);
            DataSourceSwitcher annotation = method.getAnnotation(DataSourceSwitcher.class);
            clear = annotation.clear();
            DataSourceEnum dataSource = annotation.value();
            DataSourceContextHolder.set(dataSource.getName());
            log.info("数据源切换至：{}", dataSource.getName());
            return pjp.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }
}
