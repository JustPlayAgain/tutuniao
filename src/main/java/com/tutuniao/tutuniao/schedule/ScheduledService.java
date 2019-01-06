package com.tutuniao.tutuniao.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.entity.Banner;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ScheduledService {
    private static final String academyArtUrl = "http://www.caa.edu.cn/index.html";
    public static IndexObject indexObject = null;
    @Scheduled(cron = "1 1 1 0/1 * *")
    public void scheduled(){
        indexObject =  buildIndex();
    }

    public static IndexObject buildIndex(){
        try {
            IndexObject indexObject = new IndexObject();
            HashMap<String, String> headerParams = new HashMap<>();
            headerParams.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            headerParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            headerParams.put("Accept-Encoding","gzip, deflate");
            String dataAsStringFromUrl = HttpClientUtil.getDataAsStringFromUrlWithHeader(academyArtUrl,null,500,headerParams);
            Document doc = Jsoup.parse(dataAsStringFromUrl);

            // 获取 banner
            String banner = doc.getElementsByClass("banner").toString();
            if(StringUtils.isNoneBlank(banner)){
                indexObject.setBanners(urlAddHttp(banner));
                String content = doc.getElementsByClass("container").toString();
                if(StringUtils.isNoneBlank(content)) {
                    indexObject.setContent(urlAddHttp(content));
                    return indexObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String urlAddHttp(String content) {
        content = content.replaceAll("href=\"./|href=\"/", "href=\"http://www.caa.edu.cn/");
        content = content.replaceAll("src=\"./|src=\"/", "src=\"http://www.caa.edu.cn/");
        content = content.replaceAll("src=\"images", "src=\"http://www.caa.edu.cn/images");
        return content;
    }
}
