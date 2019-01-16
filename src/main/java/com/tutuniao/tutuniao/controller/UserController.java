package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="login",method= RequestMethod.GET)
    public Response<Object> userLoging(User user, HttpServletResponse response){
        if(user == null || user.getUserName() == null || user.getUserPassword() == null ){
            return ResponseUtil.buildErrorResponse(ResponseCode.ERROR,"帐号密码不能为空");
        }
        User tmpUser = userService.queryUserByPassword(user);
        if(tmpUser != null){
            CookieUtils.addUserCookie(response, tmpUser);
            return ResponseUtil.buildSuccessResponse();
        }

        return ResponseUtil.buildErrorResponse(ResponseCode.SAMPLE_USER_NOT_EXIST,"帐号密码错误");

    }
}
