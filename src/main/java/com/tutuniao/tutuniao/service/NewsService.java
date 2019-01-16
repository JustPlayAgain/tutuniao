package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.vo.PageVO;

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
    PageVO<News> queryNewsList(News news);
}
