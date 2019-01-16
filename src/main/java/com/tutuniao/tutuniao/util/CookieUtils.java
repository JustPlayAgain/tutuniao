package com.tutuniao.tutuniao.util;

import com.tutuniao.tutuniao.common.cookie.CookieEnum;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 */
@Slf4j
@Component
public class CookieUtils {
    @Autowired
    private UserService userService;

    private static UserService service;

    @PostConstruct
    public void init() {
        service = userService;
    }

    /**
     * 登录后 把用户信息放入cookie
     * 用户ID&MD5_32(用户名密码)
     * @param response
     * @param user
     */
    public static void addUserCookie(HttpServletResponse response, User user){
        try {
            String md5User = md5User(user);
            Cookie cookie = new Cookie(CookieEnum.USER.getKey(), user.getId()+"&"+ md5User);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 从Cookei中获取用户信息
     * @param request
     * @return
     */
    public static User userVerification(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals(CookieEnum.USER.getKey())){
                String value = cookie.getValue();
                if(Utils.isNotEmpty(value)){
                    String[] split = value.split("&");
                    if (split.length == 2){
                        try {
                            Integer id = Integer.valueOf(split[0]);
                            User user = service.quertyUserById(id);
                            if(user != null && Utils.isNotNull(user.getUserName()) && Utils.isNotNull(user.getUserPassword()) ){
                                String userMd5 = md5User(user);
                                if(userMd5.equals(split[1])){
                                    return user;
                                }
                            }
                        }catch (Exception e ){
                            log.error("",e);
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * MD5_32(用户名密码)
     * @param user
     * @return
     */
    public static String md5User(User user) {
        return MD5Utils.MD5Encode(user.getUserName() + user.getUserPassword(), "utf-8");
    }


}
