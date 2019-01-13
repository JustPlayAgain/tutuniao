package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.mapper.UserMapper;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import com.tutuniao.tutuniao.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User quertyUserById(Integer userId) {
        return userMapper.queryUserById(1);
    }

    @Override
    public List<User> queryUserList(User user) {
        Page<PageInfo> pageInfo = null;
        if (Utils.isNotNull(user)) {

        }
        return null;
    }
}
