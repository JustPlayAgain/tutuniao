package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuoMeiTemplateMapper {
    /**
     * 新增活动
     * @param guoMeiTgemplate
     * @return
     */
    int insertGuoMeiTemplate(GuoMeiTemplate guoMeiTgemplate);

    /**
     * 删除活动
     * @param gmId 主键id
     * @return
     */
    int deleteGuoMeiTemplateById(Integer gmId);

    /**
     * 修改活动
     * @param gmId
     * @return
     */
    int updateGuoMeiTemplateById(Integer gmId);

    /**
     * 查询活动 支持模糊查询
     * @param guoMeiTgemplate
     * @return
     */
    List<GuoMeiTemplate> queryGuoMeiTemplateList(GuoMeiTemplate guoMeiTgemplate);
}
