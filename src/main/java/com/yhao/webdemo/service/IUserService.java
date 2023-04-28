package com.yhao.webdemo.service;

import com.yhao.webdemo.dao.model.Resource;
import com.yhao.webdemo.dao.model.User;

import java.util.List;

public interface IUserService {

    User getUserByName(String username);

    List<Resource> getUserPermission(Integer userId);

    boolean save(User user);
}
