package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityMapper {

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
    int deleteActivityById(Activity activityId);

    /**
     * 修改活动
     * @param activityId
     * @return
     */
    int updateActivityById(Activity activityId);

    /**
     * 查询活动 支持模糊查询
     * @param activity
     * @return
     */
    List<Activity> queryActivityList(Activity activity);


    /**
     * 根据ID 查询活动
     * @param activity
     * @return
     */
    Activity queryActivityById(Activity activity);
}
