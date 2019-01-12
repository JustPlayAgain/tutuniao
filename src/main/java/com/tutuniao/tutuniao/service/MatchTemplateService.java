package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.MatchTemplate;

import java.util.List;

public interface MatchTemplateService {

    /**
     * 新增活动
     * @param activity
     * @return
     */
    int insertMatchTemplate(MatchTemplate activity);

    /**
     * 删除活动
     * @param activityId 主键id
     * @return
     */
    int deleteMatchTemplateById(Integer activityId);

    /**
     * 修改活动
     * @param activityId
     * @return
     */
    int updateMatchTemplateById(Integer activityId);

    /**
     * 查询活动 支持模糊查询
     * @param activity
     * @return
     */
    List<MatchTemplate> queryMatchTemplateList(MatchTemplate activity);
}
