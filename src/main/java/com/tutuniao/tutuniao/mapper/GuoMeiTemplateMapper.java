package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuoMeiTemplateMapper {
    /**
     * 新增活动
     * @param activity
     * @return
     */
    int insertGuoMeiTemplate(GuoMeiTemplate activity);

    /**
     * 删除活动
     * @param activityId 主键id
     * @return
     */
    int deleteGuoMeiTemplateById(Integer activityId);

    /**
     * 修改活动
     * @param activityId
     * @return
     */
    int updateGuoMeiTemplateById(Integer activityId);

    /**
     * 查询活动 支持模糊查询
     * @param activity
     * @return
     */
    List<GuoMeiTemplate> queryGuoMeiTemplateList(GuoMeiTemplate activity);
}
