package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.filter.CommonFilter;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class UserController extends CommonFilter {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Response<Object> userLoging(@RequestBody User user, HttpServletResponse response, HttpServletRequest request){
        if(user == null || user.getUserName() == null || user.getUserPassword() == null ){
            return ResponseUtil.buildErrorResponse(ResponseCode.USER_PASSWORD_NULL);
        }
        User tmpUser = userService.queryUserByPassword(user);
        if(tmpUser != null){
//            CookieUtils.addUserCookie(response, tmpUser);
            String md5User = CookieUtils.md5User(user);
            tmpUser.setUuid(tmpUser.getId()+"&"+ md5User);
            tmpUser.setUserPassword("");
            return ResponseUtil.buildResponse(tmpUser);
        }

        return ResponseUtil.buildErrorResponse(ResponseCode.SAMPLE_USER_NOT_EXIST);

    }
}
