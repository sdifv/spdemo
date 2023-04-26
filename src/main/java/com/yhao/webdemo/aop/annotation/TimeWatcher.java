package com.yhao.webdemo.aop.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface TimeWatcher {
    String dscp() default "";
}
