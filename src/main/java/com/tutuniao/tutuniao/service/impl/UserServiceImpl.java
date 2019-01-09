package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.mapper.UserMapper;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User quertyUser(Integer userId) {
        return userMapper.queryUserById(1);
    }
}
