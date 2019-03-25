package com.tutuniao.tutuniao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.service.IndexService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.RedisUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ActivityService activityService;

    @Override
    public IndexObject refreshActivityList() {
        IndexObject indexObject = null;
        Activity activity = new Activity();
        activity.setPageIndex(1);
        activity.setPageSize(20);
        PageVO<List<Activity>> listPageVO = activityService.queryActivityList(activity);
        if (listPageVO != null){
            List<Activity> t = listPageVO.getT();
            if(t != null ){
                indexObject = new IndexObject();
                indexObject.setActivityList(t);
                RedisUtil.set(Constant.Redis_Activity, JSONObject.toJSONString(indexObject));
            }

        }
        return indexObject;
    }

    @Override
    public IndexObject refreshNewsList() {
        IndexObject indexObject = null;
        News news = new News();
        news.setPageIndex(1);
        news.setPageSize(3);
        PageVO<List<News>> listPageVO = newsService.queryNewsList(news);
        if (listPageVO != null){
            List<News> t = listPageVO.getT();
            if(t != null ){
                indexObject = new IndexObject();
                indexObject.setNewsList(t);
                RedisUtil.set(Constant.Redis_News, JSONObject.toJSONString(indexObject));
            }
        }
        return indexObject;
    }
}
