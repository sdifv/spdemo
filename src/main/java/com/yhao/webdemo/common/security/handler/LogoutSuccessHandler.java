package com.yhao.webdemo.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.common.security.JwtUtil;
import com.yhao.webdemo.controller.model.Result;
import com.yhao.webdemo.controller.model.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.setContentType(JwtConst.JSON_TYPE);
        response.getWriter().println(JSONUtil.parse(Result.failure(ResultEnum.LOGOUT_SUCCESS.getMessage())));
    }
}
