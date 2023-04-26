package com.yhao.webdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userTest")
public class UserController {

//    @Resource
//    private UserService userService;
//
//    @GetMapping("/index")
//    public Result<String> index() {
//        return Result.success("hello user!");
//    }
//
//    @PostMapping("/saveUser")
//    public Result<Integer> saveUser(UserPO userPO) {
//        System.out.println(userPO);
//        Integer uid = userService.save(userPO);
//        return Result.success(uid);
//    }
//
//    @GetMapping("/getUser")
//    public Result<UserPO> getUser(@RequestParam("id") Integer uid) {
//        UserPO user = userService.getUserById(uid);
//        return Result.success(user);
//    }
//
//    @GetMapping("/getAllUser")
//    public Result<List<UserPO>> getAllUser() {
//        List<UserPO> userList = userService.listUser();
//        return Result.success(userList);
//    }
}
