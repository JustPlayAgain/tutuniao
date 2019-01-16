package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.mapper.NewsMapper;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Autowired
    public NewsMapper newsMapper;

    @Override
    public int insertNews(News news) {
        if(Utils.isNotNull(news.getNewsUrl())
        && Utils.isNotNull(news.getNewsPic())
        && Utils.isNotNull(news.getNewsIsAble())
        && Utils.isNotNull(news.getNewsTitle())
        && Utils.isNotNull(news.getCreateUser())){
            news.setCreateDate(new Date());
            news.setUpdateUser(news.getCreateUser() );
            news.setUpdateDate(new Date());
            return newsMapper.insertNews(news);
        }
        return 0;
    }

    @Override
    public PageVO<News> queryNewsList(News news) {
        Page<PageInfo> pageInfo = null;
        boolean flag = false;
        if (Utils.isNotNull(news.getPageIndex()) && Utils.isNotNull(news.getPageSize())) {
            pageInfo = PageHelper.startPage(news.getPageIndex(), news.getPageSize());
            flag = true;
        }

        log.info("查询【用户数据】开始 参数为:{}", Jackson2Helper.toJsonString(news));
        List<News> newsList = newsMapper.queryNewsList(news);
        log.info("查询【用户数据】结束 结果为:{}", Jackson2Helper.toJsonString(newsList));

        return new PageVO(flag ? (int) pageInfo.getTotal() : newsList.size(), newsList);
    }

    @Override
    public News queryNewById(News news) {
        if(Utils.isNotNull(news.getId())){
            return newsMapper.queryNewsById(news);
        }
        return null;
    }

    @Override
    public int updateNewsById(News news) {
        newsMapper.updateNewsById(news);
        return 0;
    }
}
