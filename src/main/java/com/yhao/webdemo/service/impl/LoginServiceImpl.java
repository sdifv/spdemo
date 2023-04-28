package com.yhao.webdemo.service.impl;

import com.yhao.webdemo.common.security.JwtUtil;
import com.yhao.webdemo.common.security.LocalUserDetails;
import com.yhao.webdemo.common.security.LocalUserDetailsService;
import com.yhao.webdemo.common.security.SecurityException;
import com.yhao.webdemo.common.util.RedisUtil;
import com.yhao.webdemo.dao.model.User;
import com.yhao.webdemo.service.ILoginService;
import com.yhao.webdemo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LocalUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean registry(User newUser) throws SecurityException {
        User existOne = userService.getUserByName(newUser.getUsername());
        if (existOne != null) {
            throw new SecurityException("用户名已存在");
        }
        newUser.setCreateTime(new Date());
        newUser.setUpdateTime(new Date());
        return userService.save(newUser);
    }

    @Override
    public String login(String username, String password) throws SecurityException {
        try {
            LocalUserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            log.info("登录成功: {}", username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = jwtUtil.generateToken(userDetails.getUsername());
            redisUtil.setEx(username, token, jwtUtil.getExpiration(), TimeUnit.SECONDS);
            return token;
        } catch (AuthenticationException e) {
            log.error("登录失败: {}", e.getMessage());
            throw new SecurityException("登录失败");
        }
    }
}
