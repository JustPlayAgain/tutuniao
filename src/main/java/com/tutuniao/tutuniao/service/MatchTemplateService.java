package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.vo.PageVO;

import java.io.InputStream;
import java.util.List;

public interface MatchTemplateService {

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
    PageVO<List<MatchTemplate>> queryMatchTemplateList(MatchTemplate matchTemplate);

    /**
     * excel导入比赛证书信息
     * @param file
     */
    Response importMatchData(InputStream file, String name);
}
