package com.tutuniao.tutuniao.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;

        String uuid = (String) request.getSession().getAttribute("uuid");
        if (Utils.isEmpty(uuid)) {
            response.getWriter().write(JSONObject.toJSONString(ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN)));
        } else {
            flag = true;
        }
        return flag;

        /*String uuid = request.getHeader("uuid");
        if(Utils.isNotEmpty(uuid)){
            String[] split = uuid.split("&");
            if (split.length == 3) {
                try {
                    if(sim.format(new Date()).equals(split[2])){
                        Integer id = Integer.valueOf(split[0]);
                        User user = service.quertyUserById(id);
                        if(user != null && Utils.isNotNull(user.getUserName()) && Utils.isNotNull(user.getUserPassword()) ){
                            String userMd5 = md5User(user);
                            if(userMd5.equals(split[1])){
                                return user;
                            }
                        }
                    }
                }catch (Exception e ){
                    log.error("",e);
                }
            }
        }
        return null;*/
    }
}
