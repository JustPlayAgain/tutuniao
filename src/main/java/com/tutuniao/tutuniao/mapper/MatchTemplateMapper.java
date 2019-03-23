package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.MatchTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchTemplateMapper {

    /**
     * 根据id查询图图鸟证书
     * @param mtId
     * @return
     */
    MatchTemplate queryMatchTemplateById(Integer mtId);

    /**
     * 新增图图鸟证书
     * @param matchTemplate
     * @return
     */
    int insertMatchTemplate(MatchTemplate matchTemplate);

    /**
     * 删除图图鸟证书
     * @param mtId 主键id
     * @return
     */
    int deleteMatchTemplateById(Integer mtId);

    /**
     * 修改图图鸟证书
     * @param matchTemplate
     * @return
     */
    int updateMatchTemplateById(MatchTemplate matchTemplate);

    /**
     * 查询图图鸟证书 支持模糊查询
     * @param matchTemplate
     * @return
     */
    List<MatchTemplate> queryMatchTemplateList(MatchTemplate matchTemplate);
    List<MatchTemplate> matchTemplateList(MatchTemplate matchTemplate);
}
