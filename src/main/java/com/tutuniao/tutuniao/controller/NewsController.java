package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping(value="newList",method= RequestMethod.GET)
    public Response<PageVO<News>> queryNewsList(News news){

        PageVO<News> newsPageVO = newsService.queryNewsList(news);
        return ResponseUtil.buildResponse(newsPageVO);
    }

    @RequestMapping(value="insertNew",method= RequestMethod.GET)
    public Response<String> inserNew(News news, HttpServletRequest request){
        if(CookieUtils.userVerification(request)){
            int i = newsService.insertNews(news);
            if (i != 0){
                return ResponseUtil.buildResponse("新增新闻成功");
            }else{
                return ResponseUtil.buildErrorResponse(ResponseCode.PARAM_ERROR, "新增新闻失败");
            }
        }else{
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }

    }
}
