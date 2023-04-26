package com.yhao.webdemo.dao.mapper;

import com.yhao.webdemo.dao.mapper.db1.UserMapper;
import com.yhao.webdemo.dao.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        userMapper.insert(new User("AAA", "123"));
        System.out.println(userMapper.selectList(null));
    }
}