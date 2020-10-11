package com.jason.blog.mapper;

import com.jason.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    //根据username查找
    Optional<User> findByUsername(String username);

}
