package com.yhao.webdemo.controller;

import com.yhao.webdemo.common.distLock.RedisLock;
import com.yhao.webdemo.common.distLock.RedisLockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @GetMapping("/")
    public String index() {
        return "hello world !";
    }

    @GetMapping("/error")
    public String error() {
        return "something wrong !";
    }

    @GetMapping("/timeout")
    @RedisLock(lockType = RedisLockTypeEnum.ONE)
    public String timeout(@RequestParam("cost") long timeCost) {
        long startTime = System.currentTimeMillis();
        try {
            log.info("任务开始...");
            Thread.sleep(timeCost * 1000);
            log.info("任务结束...");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return String.format("任务执行时间：%d", (System.currentTimeMillis() - startTime) / 1000);
    }
}
