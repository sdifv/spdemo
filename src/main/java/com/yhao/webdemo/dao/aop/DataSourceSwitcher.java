package com.yhao.webdemo.dao.aop;

import com.yhao.webdemo.dao.dynamic.DataSourceEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSwitcher {

    DataSourceEnum value() default DataSourceEnum.MASTER;

    boolean clear() default true;
}
