package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.IndexService;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private IndexService indexService;

    @RequestMapping("/queryNewsList")
    public Response<PageVO<List<News>>> queryNewsList(@RequestBody News news){

        PageVO<List<News>> newsPageVO = newsService.queryNewsList(news);
        return ResponseUtil.buildResponse(newsPageVO);
    }

    /**
     * 新增文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping("/insertNews")
    public Response<String> insertNews(News news, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNull(user)) {
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        if (Utils.isNull(news)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.INSERT_DATA_ERROR);
        }
        news.setCreateUser(user.getUserName());
        news.setCreateDate(new Date());
        news.setUpdateUser(user.getUserName());
        news.setUpdateDate(new Date());
        int i = newsService.insertNews(news);
        if (i != 0) {
            indexService.refreshNewsList();
            return ResponseUtil.buildSuccessResponse();
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.INSERT_DATA_ERROR);
    }

    /**
     * 根据ID 查询文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping("/queryNewsById")
    public Response<News> queryNewsById(News news, HttpServletRequest request){

        if( Utils.isNotNull(news) && Utils.isNotNull(news.getId()) ){
            News tmpNews = newsService.queryNewById(news);
            if(Utils.isNotNull(tmpNews)){
                return ResponseUtil.buildResponse(tmpNews);
            }
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.SELECT_DATA_ERROR);
    }

    /**
     * 根据ID 更新文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping("/updateNews")
    public Response<String> updateNews(News news, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNull(user)) {
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        if (Utils.isNull(news)) {
            return ResponseUtil.buildErrorResponse(ErrorEnum.UPDATE_DATA_ERROR);
        }
         news.setUpdateUser(user.getUserName());
        news.setUpdateDate(new Date());
        newsService.updateNewsById(news);
        indexService.refreshNewsList();
        return ResponseUtil.buildSuccessResponse();
    }

}
