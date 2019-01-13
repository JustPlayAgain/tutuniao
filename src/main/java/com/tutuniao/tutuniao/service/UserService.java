package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    User quertyUserById(Integer userId);

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<User> queryUserList(User user);
}
