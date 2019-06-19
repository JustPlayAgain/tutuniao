package com.tutuniao.tutuniao.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.util.HttpClientUtils;
import com.tutuniao.tutuniao.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    private static final String academyArtUrl = "http://www.caa.edu.cn/index.html";
//    @Scheduled(cron = "1 1 1 0/1 * *")
    public void scheduled(){
         buildIndex();
    }
    public static IndexObject getIndexObject(String key){
        IndexObject indexObject = JSONObject.parseObject(RedisUtil.get(key), IndexObject.class);
        if(indexObject == null ){
            indexObject = ScheduledService.buildIndex();
        }
        return indexObject;
    }
    public static IndexObject buildIndex(){
        try {
            IndexObject tmpIndexObject = new IndexObject();

            String dataAsStringFromUrl = HttpClientUtils.doGet(academyArtUrl);
            if(null != dataAsStringFromUrl){
                Document doc = Jsoup.parse(dataAsStringFromUrl);
                // 获取 banner
                String banner = doc.getElementsByClass("banner").toString();
                if(StringUtils.isNoneBlank(banner)){
                    tmpIndexObject.setBanners(urlAddHttp(banner));
                    String content = doc.getElementsByClass("container").toString();
                    if(StringUtils.isNoneBlank(content)) {
                        tmpIndexObject.setContent(urlAddHttp(content));
                        RedisUtil.set(Constant.Redis_Index,JSONObject.toJSONString(tmpIndexObject));
                        return tmpIndexObject;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String urlAddHttp(String content) {
        content = content.replaceAll("http://", "https://");
        content = content.replaceAll("href=\"./|href=\"/", "href=\"https://www.caa.edu.cn/");
        content = content.replaceAll("src=\"./|src=\"/", "src=\"https://www.caa.edu.cn/");
        content = content.replaceAll("src=\"images", "src=\"https://www.caa.edu.cn/images");
        content = content.replaceAll("target=\"_blank\"", "");

        return content;
    }

    public static String buildIndexJson(){
        JSONObject reslut = new JSONObject();
        try {
            String dataAsStringFromUrl = HttpClientUtils.doGet(academyArtUrl);
            if(null != dataAsStringFromUrl){
                Document doc = Jsoup.parse(dataAsStringFromUrl);
                // 获取 banner
                Elements dgWrappers = doc.getElementsByClass("dg-wrapper");
                if(null != dgWrappers){
                    JSONArray banners = new JSONArray();
                    for (Element dgWrapper:dgWrappers) {
                        JSONObject banner = new JSONObject();
                        String href = dgWrapper.select("a").attr("href");
                        String img = dgWrapper.select("img").attr("src");
                        if(!href.startsWith("http") && !href.equals("#")){
                            banner.put("href","http://www.caa.edu.cn/"+href);
                        }else {
                            banner.put("href",href);
                        }
                        if(!img.startsWith("http")){
                            banner.put("img","http://www.caa.edu.cn/"+img);
                        }else{
                            banner.put("img",img);
                        }

                        banners.add(banner);
                    }


                    reslut.put("banners",banners);
                }
                Elements boxs = doc.getElementsByClass("box");
                if(null != boxs){
                    Element element = boxs.get(0);
                    Elements hd = element.getElementsByClass("hd");
                    Elements bd = element.getElementsByClass("bd");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reslut.toJSONString();
    }
}
