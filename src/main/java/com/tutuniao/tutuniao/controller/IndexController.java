package com.tutuniao.tutuniao.controller;

import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.entity.*;
import com.tutuniao.tutuniao.schedule.ScheduledService;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.MD5Utils;
import com.tutuniao.tutuniao.util.RedisUtil;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private ActivityService activityService;

    @Autowired
    private GuoMeiTemplateService guoMeiTemplateService;

    @Autowired
    private MatchTemplateService matchTemplateService;


    @RequestMapping("/index")
    public IndexObject index(){
        IndexObject indexObject = ScheduledService.getIndexObject(Constant.Redis_Index);
        if(indexObject!= null && indexObject.getNewsList() == null){
            indexObject.setNewsList(news().getNewsList());
            indexObject.setActivityList(activity().getActivityList());
//            setNewsList(indexObject);
//            setActivityList(indexObject);
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
    public Response queryInfo(String username, String idCard, int activityId, int flag){
        if (Utils.isEmpty(idCard) && Utils.isEmpty(username)) { // 验证数据
            return ResponseUtil.buildErrorResponse(ErrorEnum.GUOMEITEMPLATE_ERROR);
        }
        if (Utils.isNull(flag)) { // 如果flag为空 查询国美比赛测评
            GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();
            guoMeiTemplate.setStudentName(username);
            guoMeiTemplate.setIdCard(idCard);
            guoMeiTemplate.setActId(activityId);
            GuoMeiTemplate template = guoMeiTemplateService.queryGuoMeiTemplate(guoMeiTemplate);
            if (Utils.isNull(template)) { // 数据不存在
                return ResponseUtil.buildErrorResponse(ErrorEnum.GUOMEITEMPLATE_NULL);
            }
            String str = MD5Utils.MD5Encode(template.getIdCard(), "utf-8");
            template.setIdCard(str);
            return ResponseUtil.buildResponse(template);
        } else { // 否则查询结业证书情况
            MatchTemplate match = new MatchTemplate();
            match.setStudentName(username);
            match.setIdCard(idCard);
            PageVO<List<MatchTemplate>> listPageVO = matchTemplateService.queryMatchTemplateList(match);
            List<MatchTemplate> list = listPageVO.getT();
            if (Utils.isNull(list) && list.size() == 0) { // 数据不存在
                return ResponseUtil.buildErrorResponse(ErrorEnum.MATCHTEMPLATE_NULL);
            }
            for (int i = 0; i < list.size() ; i++) {
                String md5_idCard = list.get(i).getIdCard();
                list.get(i).setIdCard(md5_idCard);
            }
            return ResponseUtil.buildResponse(list);
        }
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
                RedisUtil.set(Constant.Redis_News, JSONObject.toJSONString(indexObject));
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
                RedisUtil.set(Constant.Redis_Activity, JSONObject.toJSONString(indexObject));
            }

        }
    }
}

