package com.tutuniao.tutuniao.controller;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.entity.User;
import com.tutuniao.tutuniao.service.NewsService;
import com.tutuniao.tutuniao.util.CookieUtils;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.util.response.Response;
import com.tutuniao.tutuniao.util.response.ResponseCode;
import com.tutuniao.tutuniao.util.response.ResponseUtil;
import com.tutuniao.tutuniao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping(value="newList",method= RequestMethod.GET)
    public Response<PageVO<News>> queryNewsList(News news){

        PageVO<News> newsPageVO = newsService.queryNewsList(news);
        return ResponseUtil.buildResponse(newsPageVO);
    }

    /**
     * 新增文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping(value="insertNew",method= RequestMethod.GET)
    public Response<String> inserNew(News news, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNotNull(user)){
            if(Utils.isNotNull(news.getNewsUrl()) && Utils.isNotNull(news.getNewsPic()) && Utils.isNotNull(news.getNewsIsAble()) && Utils.isNotNull(news.getNewsTitle())) {
                news.setCreateUser(user.getUserName());
                news.setCreateDate(new Date());
                news.setUpdateUser(user.getUserName());
                news.setUpdateDate(new Date());
                int i = newsService.insertNews(news);
                if (i != 0) {
                    return ResponseUtil.buildSuccessResponse();
                }
            }
        }else{
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.INSERT_DATA_ERROR);
    }

    /**
     * 根据ID 查询文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping(value="querNewsById",method= RequestMethod.GET)
    public Response<News> querNewsById(News news, HttpServletRequest request){
        if(CookieUtils.userVerification(request) != null){
            News tmpNews = newsService.queryNewById(news);
            if(Utils.isNotNull(tmpNews)){
                return ResponseUtil.buildResponse(tmpNews);
            }
        }else{
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.SELECT_DATA_ERROR);
    }

    /**
     * 根据ID 更新文章
     * @param news
     * @param request
     * @return
     */
    @RequestMapping(value="updateNews",method= RequestMethod.GET)
    public Response<String> updateNew(News news, HttpServletRequest request){
        User user = CookieUtils.userVerification(request);
        if(Utils.isNotNull(user)){
            if(Utils.isNotNull(news.getId()) && Utils.isNotNull(news.getNewsUrl()) && Utils.isNotNull(news.getNewsPic()) && Utils.isNotNull(news.getNewsIsAble()) && Utils.isNotNull(news.getNewsTitle())) {
                news.setUpdateUser(user.getUserName());
                news.setUpdateDate(new Date());
                newsService.updateNewsById(news);
                return ResponseUtil.buildSuccessResponse();
            }
        }else{
            return ResponseUtil.buildErrorResponse(ResponseCode.NEED_LOGIN);
        }
        return ResponseUtil.buildErrorResponse(ErrorEnum.UPDATE_DATA_ERROR);
    }

}
