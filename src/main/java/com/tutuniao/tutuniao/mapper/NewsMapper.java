package com.tutuniao.tutuniao.mapper;

import com.tutuniao.tutuniao.entity.News;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper {
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
