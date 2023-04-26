package com.yhao.webdemo.dao;

import com.yhao.webdemo.dao.mapper.db1.GoodMapper;
import com.yhao.webdemo.dao.mapper.db1.UserMapper;
import com.yhao.webdemo.dao.model.Good;
import com.yhao.webdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultiDataSourceTest {

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void selectFromMultiDBs() {
        goodMapper.insert(new Good(10L, 1));
    }
}
