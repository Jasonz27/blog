package com.jason.blog.service;

import com.jason.blog.domain.User;

public interface LoginService {

    //检查用户信息
    User login(String username,String password);
}
