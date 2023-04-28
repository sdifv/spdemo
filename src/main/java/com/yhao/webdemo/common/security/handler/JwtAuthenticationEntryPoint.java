package com.yhao.webdemo.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.controller.model.Result;
import com.yhao.webdemo.controller.model.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("authenticate fail: {}", authException.getMessage());
        response.setContentType(JwtConst.JSON_TYPE);
        response.getWriter().println(JSONUtil.parse(Result.failure(ResultEnum.AUTHENTICATION_FAILED.getMessage())));
        response.getWriter().flush();
    }
}
