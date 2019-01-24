package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.mapper.UserMapper;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.vo.PageVO;
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
        return userMapper.queryUserById(userId);
    }

    @Override
    public PageVO<User> queryUserList(User user) {
        Page<PageInfo> pageInfo = null;
        boolean flag = false;
        if (Utils.isNotNull(user.getPageIndex()) && Utils.isNotNull(user.getPageSize())) {
            pageInfo = PageHelper.startPage(user.getPageIndex(), user.getPageSize());
            flag = true;
        }

        log.info("查询【用户数据】开始 参数为:{}", Jackson2Helper.toJsonString(user));
        List<User> userList = userMapper.queryUserList(user);
        log.info("查询【用户数据】结束 结果为:{}", Jackson2Helper.toJsonString(userList));

        return new PageVO(flag ? (int) pageInfo.getTotal() : userList.size(), userList);
    }

    @Override
    public User queryUserByPassword(User user) {
        return userMapper.queryUserByPassword(user);
    }
}
