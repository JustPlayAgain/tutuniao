package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.schedule.ScheduledService;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private ActivityService activityService;

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
            setActivityList(indexObject);
        }
        return indexObject;
    }
    @RequestMapping("/activity")
    public IndexObject activity(){

        IndexObject indexObject = ScheduledService.indexObject;
        if(indexObject == null || indexObject.getActivityList() == null ){
            indexObject = new IndexObject();
            setActivityList(indexObject);
        }
        return indexObject;
    }

    @PostMapping("/search")
    public HashMap search(String username,String idCart, int activityId ){
        HashMap<String, String> map = new HashMap<>();
        map.put("username",username);
        map.put("idCart",idCart);
        return map;
    }

    @RequestMapping("/refreshIndex")
    public void refreshIndex(){
        IndexObject indexObject = ScheduledService.buildIndex();
            if(null != indexObject){
                ScheduledService.indexObject = indexObject;
                setNewsList(ScheduledService.indexObject);
                setActivityList(ScheduledService.indexObject);
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

    @RequestMapping("/refreshActivity")
    public void refreshActivity(){

        IndexObject indexObject = ScheduledService.indexObject;
        if(indexObject == null){
            refreshIndex();
        }
        setActivityList(ScheduledService.indexObject);
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

    private void setActivityList(IndexObject indexObject) {
        Activity activity = new Activity();
        activity.setPageIndex(1);
        activity.setPageSize(20);
        PageVO<List<Activity>> listPageVO = activityService.queryActivityList(activity);
        if (listPageVO != null){
            List<Activity> t = listPageVO.getT();
            if(t != null ){
                if(indexObject == null)
                    indexObject = new IndexObject();
                indexObject.setActivityList(t);
            }

        }
    }

}
