package com.yhao.webdemo.service;

import com.yhao.webdemo.config.SvrConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class TimeWatchServiceTest {

    @Test
    public void test() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SvrConfig.class);
        TimeWatchService service = context.getBean(TimeWatchService.class);
        System.out.println(service.sum(1, 2));
    }
}