package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.vo.PageVO;

import java.util.List;

public interface NewsService {

    /**
     * 新增文章
     * @param news
     * @return
     */
    int insertNews(News news);

    /**
     * 查询文章列表
     * @param news
     * @return
     */
    PageVO<List<News>> queryNewsList(News news);

    /**
     * 根据文章ID 查询文章
     * @param news
     * @return
     */
    News queryNewById(News news);

    /**
     * 更具文章ID 更新文章
     * @param news
     * @return
     */
    int updateNewsById(News news);
}
