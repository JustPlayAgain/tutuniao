package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.mapper.UserMapper;
import com.tutuniao.tutuniao.service.UserService;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void quertyUser() {
        User user = userMapper.queryUserById(1);
        log.info("登录用户信息：{} ========", user);
    }

    @Test
    public void quertyUserList() {
        User user = new User();
        user.setPageIndex(1);
        user.setPageSize(1);
        PageVO<User> pageVO = userService.queryUserList(user);
        log.info("登录用户信息:======== {}", pageVO);
    }
}