package com.tutuniao.tutuniao.controller;

import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.schedule.ScheduledService;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.RedisClusterUtil;
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
        IndexObject indexObject = ScheduledService.getIndexObject(Constant.Redis_Index);
        if(indexObject!= null && indexObject.getNewsList() == null){
//            indexObject.setNewsList(news().getNewsList());
//            indexObject.setActivityList(activity().getActivityList());
            setNewsList(indexObject);
            setActivityList(indexObject);
        }
        return indexObject;
    }
    @RequestMapping("/activity")
    public IndexObject activity(){
        IndexObject indexObject = ScheduledService.getIndexObject(Constant.Redis_Activity);
        if(indexObject == null || indexObject.getActivityList() == null ){
            indexObject = new IndexObject();
            setActivityList(indexObject);
        }
        return indexObject;
    }

    /**
     * 先判断redis 是否有新闻 如果有 就
     * @return
     */
    @RequestMapping("/news")
    public IndexObject news(){
        IndexObject indexObject = ScheduledService.getIndexObject(Constant.Redis_News);
        if(indexObject == null || indexObject.getNewsList() == null ){
            indexObject = new IndexObject();
            setNewsList(indexObject);
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

    /**
     * 重新获取 首页信息
     * @return
     */
    @RequestMapping("/refreshIndex")
    public IndexObject refreshIndex(){
        IndexObject indexObject = ScheduledService.buildIndex();
        return indexObject;
    }

    /**
     * 刷新新闻
     * @return
     */
    @RequestMapping("/refreshNews")
    public IndexObject refreshNews(){
        IndexObject indexObject = new IndexObject();
        setNewsList(indexObject);
        return indexObject;
    }

    /**
     * 刷新活动
     * @return
     */
    @RequestMapping("/refreshActivity")
    public IndexObject refreshActivity(){
        IndexObject indexObject = new IndexObject();
        setActivityList(indexObject);
        return indexObject;
    }

    /**
     * 从数据库中取出 三条新闻 并放入Redis中
     * @param indexObject
     */
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
                RedisClusterUtil.set(Constant.Redis_News, JSONObject.toJSONString(indexObject));
            }

        }
    }

    /**
     * 从数据库中取出 20条活动 并放入Redis中
     * @param indexObject
     */
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
                RedisClusterUtil.set(Constant.Redis_Activity, JSONObject.toJSONString(indexObject));
            }

        }
    }
}

