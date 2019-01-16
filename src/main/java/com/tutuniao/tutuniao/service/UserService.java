package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.vo.PageVO;

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

    /**
     * 根据账户密码查询用户
     * @param user
     * @return
     */
    User queryUserByPassword(User user);
}
