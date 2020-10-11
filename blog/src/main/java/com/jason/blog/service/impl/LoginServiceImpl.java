package com.jason.blog.service.impl;

import com.jason.blog.domain.User;
import com.jason.blog.mapper.UserMapper;
import com.jason.blog.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    //检查用户信息
    @Override
    public User login(String username, String password) {
        User user;
        Optional<User> byUsername = userMapper.findByUsername(username);
        if (byUsername.isPresent())
            user = byUsername.get();
        else
            return null;
        if (check(password, user.getPassword()))
            return user;
        else
            return null;
    }

    private boolean check(String input, String password) {
        if (password.equals(input))
            return true;
        else
            return false;
    }
}
