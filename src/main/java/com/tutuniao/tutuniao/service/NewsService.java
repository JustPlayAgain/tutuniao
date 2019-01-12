package com.tutuniao.tutuniao.service;

import com.tutuniao.tutuniao.entity.News;

import java.util.List;

public interface NewsService {

    /**
     * 新增文章
     * @param news
     * @return
     */
    News insertNews(News news);

    /**
     * 查询文章列表
     * @param news
     * @return
     */
    List<News> queryNewsList(News news);
}
