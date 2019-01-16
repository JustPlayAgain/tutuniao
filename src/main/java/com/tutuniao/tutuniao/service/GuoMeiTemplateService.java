package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.util.response.Response;

import java.io.File;
import java.util.List;

public interface GuoMeiTemplateService {

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

    /**
     * excel导入国美证书信息
     * @param file
     */
    Response importGuoMeiData(File file);
}
