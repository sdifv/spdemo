package com.yhao.webdemo.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.controller.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(JwtConst.JSON_TYPE);
        response.getWriter().println(JSONUtil.parse(Result.failure(accessDeniedException.getMessage())));
    }
}
