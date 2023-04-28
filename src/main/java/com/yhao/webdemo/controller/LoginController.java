package com.yhao.webdemo.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.google.common.io.BaseEncoding;
import com.yhao.webdemo.common.security.JwtConst;
import com.yhao.webdemo.common.security.KaptchaConfig;
import com.yhao.webdemo.common.security.SecurityException;
import com.yhao.webdemo.common.util.RedisUtil;
import com.yhao.webdemo.controller.model.Result;
import com.yhao.webdemo.controller.model.ResultEnum;
import com.yhao.webdemo.dao.model.User;
import com.yhao.webdemo.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ILoginService loginService;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String userKey = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        String base64Img = "data:image/jpeg;base64," + BaseEncoding.base64().encode(outputStream.toByteArray());
        String key = KaptchaConfig.CAPTCHA_CHAHE_PFX + UUID.randomUUID();
        redisUtil.setEx(key, code, 120, TimeUnit.SECONDS);
        return Result.success(MapUtil.builder().put(JwtConst.CAPTCHA_MARK, userKey).put("code", code).build());
    }

    @GetMapping("/privatePage")
    public Result privatePage() {
        return Result.success("It's a private page");
    }

    @GetMapping("/publicPage")
    public Result publicPage() {
        return Result.success("It's a public page");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        try {
            String token = loginService.login(user.getUsername(), user.getPassword());
            return Result.success(MapUtil.builder().put("token", token).build());
        } catch (SecurityException e) {
            return Result.failure(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public Result logout() {
        return Result.success(ResultEnum.LOGOUT_SUCCESS);
    }
}
