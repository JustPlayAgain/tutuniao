package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.mapper.NewsMapper;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NewsServiceImplTest {
    @Autowired
    private NewsMapper newsMapper;

//    @Test
    public void insertNews() {
        News news = new News();
        news.setNewsIsAble(0);
        news.setNewsUrl("http://www.baidu.com");
        news.setNewsPic("https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg");
        news.setNewsTitle("百度测试");
        news.setCreateUser("yugc");
        news.setCreateDate(new Date());
        news.setUpdateUser("yugc");
        news.setUpdateDate(new Date());
        int i = newsMapper.insertNews(news);
        System.out.println(Jackson2Helper.toJsonStringNotNull(i));
        System.out.println(news.getId());
    }

    @Test
    public void queryNewsList() {
    }

//    @Test
    public void updateNewsById() throws Exception {
        News news = new News();
        news.setId(1);
        news.setNewsIsAble(0);
        news.setNewsUrl("http://www.baidu.com");
        news.setNewsPic("https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg");
        news.setNewsTitle("百度测试");
        news.setUpdateUser("yugc11111");
        news.setUpdateDate(new Date());
        int i = newsMapper.updateNewsById(news);
        System.out.println(i);
    }
}