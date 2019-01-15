package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.vo.PageVO;

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
    PageVO<User> queryUserList(User user);
}
