package com.yhao.webdemo.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SourceNotFoundHandler {

    @ResponseBody
    @ExceptionHandler(value = SourceNotFoundException.class)
    public String exception(SourceNotFoundException e) {
        return "source is not found !";
    }
}
