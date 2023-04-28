package com.yhao.webdemo.common.security;

import com.yhao.webdemo.dao.model.Resource;
import com.yhao.webdemo.dao.model.User;
import com.yhao.webdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    private IUserService userService;

    @Override
    public LocalUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if (user != null) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            List<Resource> permissions = userService.getUserPermission(user.getId());
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println(user);
            return new LocalUserDetails(user, permissions);
        }
        return null;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        final User existOne = userService.getUserByName(user.getUsername());
        existOne.setPassword(newPassword);
        userService.save(existOne);
        return new LocalUserDetails(existOne, new ArrayList<>());
    }

    public User currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.getUserByName(username);
    }
}
