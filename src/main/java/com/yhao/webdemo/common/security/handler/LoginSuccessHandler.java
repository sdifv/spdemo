package com.yhao.webdemo.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.common.security.JwtUtil;
import com.yhao.webdemo.controller.model.Result;
import com.yhao.webdemo.controller.model.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(JwtConst.JSON_TYPE);
        String token = jwtUtil.generateToken(authentication.getName());
        response.setHeader(JwtConst.TOKEN_HEADER, token);
        response.getWriter().println(JSONUtil.parse(Result.success(ResultEnum.LOGIN_SUCCESS)));
    }
}
