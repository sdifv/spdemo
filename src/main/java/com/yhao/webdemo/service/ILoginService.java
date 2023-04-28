package com.yhao.webdemo.service;

import com.yhao.webdemo.common.security.SecurityException;
import com.yhao.webdemo.dao.model.User;

public interface ILoginService {
    boolean registry(User user) throws SecurityException;

    String login(String username, String password) throws SecurityException;
}
