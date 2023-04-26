package com.yhao.webdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhao.webdemo.dao.mapper.db1.UserMapper;
import com.yhao.webdemo.dao.mapper.db2.GoodMapper;
import com.yhao.webdemo.dao.model.Good;
import com.yhao.webdemo.dao.model.User;
import com.yhao.webdemo.service.model.UserOrder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodMapper goodMapper;

    public User getUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "AAA");
        return userMapper.selectOne(wrapper);
    }

    public UserOrder getUserOrder(long uid) {
        User user = userMapper.selectById(uid);
        Good order = goodMapper.selectById(uid);
        return new UserOrder(user, order);
    }

}
