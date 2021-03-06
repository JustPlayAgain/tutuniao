package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<User> queryUserList(User user);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 根据账户密码查询用户
     * @param user
     * @return
     */
    User queryUserByPassword(User user);

}
