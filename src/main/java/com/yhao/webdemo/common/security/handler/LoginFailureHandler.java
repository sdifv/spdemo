package com.yhao.webdemo.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.yhao.webdemo.common.security.CaptchaExecption;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.controller.model.Result;
import com.yhao.webdemo.controller.model.ResultEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(JwtConst.JSON_TYPE);
        String errorMsg = ResultEnum.USERINFO_ERROR.getMessage();
        if (exception instanceof CaptchaExecption) {
            errorMsg = ResultEnum.CAPTCHA_ERROR.getMessage();
            response.getWriter().println(JSONUtil.parse(Result.failure(errorMsg)));
        } else {
            response.getWriter().println(JSONUtil.parse(Result.failure(errorMsg)));
        }
    }
}
