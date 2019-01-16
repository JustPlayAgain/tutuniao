package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.MatchTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchTemplateMapper {

    /**
     * 新增活动
     * @param matchTemplate
     * @return
     */
    int insertMatchTemplate(MatchTemplate matchTemplate);

    /**
     * 删除活动
     * @param mtId 主键id
     * @return
     */
    int deleteMatchTemplateById(Integer mtId);

    /**
     * 修改活动
     * @param mtId
     * @return
     */
    int updateMatchTemplateById(Integer mtId);

    /**
     * 查询活动 支持模糊查询
     * @param matchTemplate
     * @return
     */
    List<MatchTemplate> queryMatchTemplateList(MatchTemplate matchTemplate);
}
