package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.vo.PageVO;

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
     * @param activity
     * @return
     */
    int deleteActivityById(Activity activity);

    /**
     * 修改活动
     * @param activity
     * @return
     */
    int updateActivityById(Activity activity);

    /**
     * 查询所有活动 并支持 活动名称，活动码 模糊查询
     * @param activity
     * @return
     */
    PageVO<List<Activity>> queryActivityList(Activity activity);

    /**
     * 根据ID 查询活动
     * @param activity
     * @return
     */
    Activity queryActivityById(Activity activity);

}
