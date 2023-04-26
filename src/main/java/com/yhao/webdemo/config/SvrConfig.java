package com.yhao.webdemo.config;

import com.yhao.webdemo.aop.aspect.TimeWatcherAspect;
import com.yhao.webdemo.service.TimeWatchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SvrConfig {

    @Value("${spring.profiles.active:default}")
    private String mode;

    @Value("${spring.application.name:demoTest}")
    private String name;

    @Value("${server.port:8801}")
    private String port;

    @Bean
    public SvrConfig getSvrConfig() {
        return new SvrConfig();
    }

    @Bean
    @ConditionalOnExpression("${customAnnotation.timeWatcher:false}")
    public TimeWatcherAspect createTimeWatcher() {
        System.out.println(port);
        return new TimeWatcherAspect();
    }

    @Bean
    public TimeWatchService createCase() {
        return new TimeWatchService();
    }
}
