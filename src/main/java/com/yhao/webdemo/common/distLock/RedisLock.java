package com.yhao.webdemo.common.distLock;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLock {

    int lockField() default 0;

    int tryCount() default 3;

    RedisLockTypeEnum lockType();

    /**
     * 申请锁时间，单位秒 s
     *
     * @return
     */
    long lockTime() default 3;

}
