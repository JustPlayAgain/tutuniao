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
    int insertNews(News news);

    /**
     * 查询文章列表
     * @param news
     * @return
     */
    List<News> queryNewsList(News news);

    /**
     * 查询文章根据ID
     * @param news
     * @return
     */
    News queryNewsById(News news);

    /**
     * 更具文章ID 更新文章
     * @param news
     * @return
     */
    int updateNewsById(News news);
}
