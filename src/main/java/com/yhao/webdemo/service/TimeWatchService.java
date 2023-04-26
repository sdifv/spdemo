package com.yhao.webdemo.service;

import com.yhao.webdemo.aop.annotation.TimeWatcher;

public class TimeWatchService {

    @TimeWatcher(dscp = "compute sum")
    public Integer sum(int a, int b) throws InterruptedException {
        Thread.sleep(1000);
        return a + b;
    }
}
