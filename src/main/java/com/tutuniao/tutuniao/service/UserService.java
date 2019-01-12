package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.User;

public interface UserService {

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    User quertyUserById(Integer userId);
}
