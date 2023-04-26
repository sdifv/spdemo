package com.yhao.webdemo.common.distLock;

import lombok.Data;

@Data
public class RedisLockDefinitionHolder {

    private String businessKey;

    /**
     * 加锁时间（秒)
     */
    private long lockTime;

    private long lastModifyTime;

    private Thread currentThread;

    private int tryCount;

    private int currentCount;

    /**
     * 持锁时间小于该阈值则续时
     */
    private long modifyPeriod;

    public RedisLockDefinitionHolder(RedisLock redisLock, Thread thread) {
        this.businessKey = redisLock.lockType().getCode();
        this.lockTime = redisLock.lockTime();
        this.lastModifyTime = System.currentTimeMillis();
        this.currentThread = thread;
        this.tryCount = redisLock.tryCount();
        this.modifyPeriod = lockTime * 1000 / 3;
    }

    @Override
    public String toString() {
        return "RedisLockDefinitionHolder{" +
                "businessKey='" + businessKey + '\'' +
                ", lastModifyTime=" + lastModifyTime +
                ", currentCount=" + currentCount +
                '}';
    }
}
