package com.yhao.webdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/")
    public String index() {
        return "hello world !";
    }

    @GetMapping("/error")
    public String error() {
        return "something wrong !";
    }
}
