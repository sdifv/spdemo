package com.yhao.webdemo.common.security;

import com.yhao.webdemo.common.security.handler.LoginFailureHandler;
import com.yhao.webdemo.common.util.RedisUtil;
import com.yhao.webdemo.controller.model.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if ("/login".equals(url) && request.getMethod().equals("POST")) {
            try {
                verifyCaptcha(request);
            } catch (CaptchaExecption e) {
                loginFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void verifyCaptcha(HttpServletRequest request) {
        String userKey = request.getParameter(JwtConst.CAPTCHA_MARK);
        String code = request.getHeader(JwtConst.CAPTCHA_FIELD);
        String captchaKey = KaptchaConfig.CAPTCHA_CHAHE_PFX + userKey;
        String captchaValue = redisUtil.get(captchaKey);
        if (StringUtils.isBlank(userKey) || StringUtils.isBlank(captchaKey) || !code.equals(captchaValue)) {
            throw new CaptchaExecption(ResultEnum.CAPTCHA_ERROR.getMessage());
        }
        redisUtil.delete(captchaKey);
    }
}
