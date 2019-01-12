package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.Activity;

import java.util.List;

public interface ActivityService {

    /**
     * 新增活动
     * @param activity
     * @return
     */
    int insertActivity(Activity activity);

    /**
     * 删除活动
     * @param activityId 主键id
     * @return
     */
    int deleteActivityById(Integer activityId);

    /**
     * 修改活动
     * @param activityId
     * @return
     */
    int updateActivityById(Integer activityId);

    /**
     * 查询活动 支持模糊查询
     * @param activity
     * @return
     */
    List<Activity> queryActivityList(Activity activity);
}
