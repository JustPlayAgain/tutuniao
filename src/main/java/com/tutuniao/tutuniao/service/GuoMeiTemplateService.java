package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.util.response.Response;

import java.io.File;
import java.util.List;

public interface GuoMeiTemplateService {

    /**
     * 用户接口查询国美证书列表 证书编号+姓名
     * @param guoMeiTemplate
     * @return
     */
    GuoMeiTemplate queryGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate);

    /**
     * 新增活动
     * @param guoMeiTemplate
     * @return
     */
    int insertGuoMeiTemplate(GuoMeiTemplate guoMeiTemplate);

    /**
     * 删除活动
     * @param gmId 主键id
     * @return
     */
    int deleteGuoMeiTemplateById(Integer gmId);

    /**
     * 修改活动
     * @param guoMeiTemplate
     * @return
     */
    int updateGuoMeiTemplateById(GuoMeiTemplate guoMeiTemplate);

    /**
     * 查询活动 支持模糊查询
     * @param guoMeiTemplate
     * @return
     */
    List<GuoMeiTemplate> queryGuoMeiTemplateList(GuoMeiTemplate guoMeiTemplate);

    /**
     * 根据id查询国美证书
     * @param gmId
     * @return
     */
    GuoMeiTemplate queryGuoMeiTemplateById(Integer gmId);

    /**
     * excel导入国美证书信息
     * @param file
     */
    Response importGuoMeiData(File file);
}
