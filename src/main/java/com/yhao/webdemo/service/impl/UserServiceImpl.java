package com.yhao.webdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yhao.webdemo.common.util.RedisUtil;
import com.yhao.webdemo.dao.mapper.db1.UserMapper;
import com.yhao.webdemo.dao.mapper.db1.UserRoleMapper;
import com.yhao.webdemo.dao.model.Resource;
import com.yhao.webdemo.dao.model.User;
import com.yhao.webdemo.dao.model.UserRole;
import com.yhao.webdemo.service.IResourceService;
import com.yhao.webdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private static final String USER_NAME_PFX = "user_name:";

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    IResourceService resourceService;


    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<Resource> getUserPermission(Integer userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        return userRoles.parallelStream().flatMap(userRole ->
                resourceService.getResourceByRole(userRole.getRoleId()).stream()
        ).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean save(User user) {
        User existOne = getUserByName(user.getUsername());
        if (existOne != null) {
            userMapper.updateById(user);
        } else {
            userMapper.insert(user);
        }
        return true;
    }
}
