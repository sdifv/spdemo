package com.yhao.webdemo.controller;

import com.yhao.webdemo.common.util.TokenUtil;
import com.yhao.webdemo.controller.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokenTest")
public class TokenController {

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping("/getToken")
    public Result<String> getToken(@RequestParam("uid") String uid) {
        return Result.success(tokenUtil.generateToken(uid));
    }

    @PostMapping("/oncePost")
    public Result<String> oncePost(@RequestHeader("token") String token, @RequestParam("uid") String uid) {
        boolean valid = tokenUtil.validToken(token, uid);
        return valid ? Result.success("调用正常") : Result.success("调用失败");
    }

}
