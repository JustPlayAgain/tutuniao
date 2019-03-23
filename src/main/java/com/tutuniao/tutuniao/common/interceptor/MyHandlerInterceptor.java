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
        System.out.println(request.getRequestURI());
        boolean flag = false;
        String uuid = (String) request.getSession().getAttribute("uuid");
        if (Utils.isEmpty(uuid)) {
            response.getWriter().write(JSONObject.toJSONString(ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN)));
        } else {
            flag = true;
        }
        return flag;
    }
}
