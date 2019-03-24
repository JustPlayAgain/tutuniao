package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.common.filter.CommonFilter;
import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController extends CommonFilter {
    @Autowired
    private ActivityService activityService;

    /**
     * 查询所有活动 并支持 活动名称，活动码 模糊查询
     * @param activity
     * @return
     */
    @RequestMapping("/queryActivityList")
    public Response<PageVO<List<Activity>>> queryActivityList(Activity activity){
        PageVO<List<Activity>> pageVO = activityService.queryActivityList(activity);
        return ResponseUtil.buildResponse(pageVO);
    }

    /**
     * 新增一个活动
     * @param activity
     * @param request
     * @return
     */
    @RequestMapping("/insertActivity")
    public Response<String> insertActivity(Activity activity, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNull(user)) {
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        if(Utils.isNotNull(activity) && Utils.isNotNull(activity.getActivityName()) && Utils.isNotNull(activity.getActivityCode()) && Utils.isNotNull(activity.getActivityDate())){
            activity.setCreateUser(user.getUserName());
            activity.setCreateDate(new Date());
            activity.setUpdateUser(user.getUserName());
            activity.setUpdateDate(new Date());
            int i = activityService.insertActivity(activity);
            if (i != 0) {
                return ResponseUtil.buildSuccessResponse();
            }
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.INSERT_DATA_ERROR);
    }

    /**
     * 根据ID 查询活动
     * @param request
     * @return
     */
    @RequestMapping("/queryActivityById")
    public Response<Activity> queryActivityById(Activity activity, HttpServletRequest request){

        if( Utils.isNotNull(activity) && Utils.isNotNull(activity.getId()) ){
            Activity tmpActivity = activityService.queryActivityById(activity);
            if(Utils.isNotNull(tmpActivity)){
                return ResponseUtil.buildResponse(tmpActivity);
            }
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.SELECT_DATA_ERROR);
    }

    /**
     * 根据ID 更新活动
     * @param activity
     * @param request
     * @return
     */
    @RequestMapping("/updateActivity")
    public Response<String> updateActivity(Activity activity, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNull(user)) {
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        if(Utils.isNotNull(activity) && Utils.isNotNull(activity.getId()) && Utils.isNotNull(activity.getActivityName()) && Utils.isNotNull(activity.getActivityCode()) && Utils.isNotNull(activity.getActivityDate())) {
            activity.setUpdateUser(user.getUserName());
            activity.setUpdateDate(new Date());
            activityService.updateActivityById(activity);
            return ResponseUtil.buildSuccessResponse();
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.UPDATE_DATA_ERROR);
    }
}
