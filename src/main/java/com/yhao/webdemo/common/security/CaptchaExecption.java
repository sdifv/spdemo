package com.yhao.webdemo.common.security;

import org.springframework.security.core.AuthenticationException;

public class CaptchaExecption extends AuthenticationException {

    public CaptchaExecption(String message) {
        super(message);
    }
}
