package com.tutuniao.tutuniao.schedule;

import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.util.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;

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

            String dataAsStringFromUrl = HttpClientUtils.get(academyArtUrl);
            if(null != dataAsStringFromUrl){
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
