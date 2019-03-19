package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.schedule.ScheduledService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private NewsService newsService;

    @RequestMapping("/index")
    public IndexObject index(){
        IndexObject indexObject;
        if( (indexObject = ScheduledService.indexObject) == null){
            indexObject = ScheduledService.buildIndex();
            if(null != indexObject){
                ScheduledService.indexObject = indexObject;
            }
        }
        if(indexObject!= null && indexObject.getNewsList() == null){
            setNewsList(indexObject);
        }
        return indexObject;
    }

    @RequestMapping("/refreshIndex")
    public void refreshIndex(){
        IndexObject indexObject = ScheduledService.buildIndex();
            if(null != indexObject){
                ScheduledService.indexObject = indexObject;
            }
    }

    @RequestMapping("/refreshNews")
    public void refreshNews(){

        IndexObject indexObject = ScheduledService.indexObject;
        if(indexObject == null){
            refreshIndex();
        }
        setNewsList(ScheduledService.indexObject);
    }

    private void setNewsList(IndexObject indexObject) {
        News news = new News();
        news.setPageIndex(1);
        news.setPageSize(3);
        PageVO<List<News>> listPageVO = newsService.queryNewsList(news);
        if (listPageVO != null){
            List<News> t = listPageVO.getT();
            if(t != null ){
                if(indexObject == null)
                    indexObject = new IndexObject();
                indexObject.setNewsList(t);
            }

        }
    }

}
