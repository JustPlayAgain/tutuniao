package com.tutuniao.tutuniao.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.util.HttpClientUtils;
import com.tutuniao.tutuniao.util.RedisUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class ScheduledService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Scheduled(cron = "1 1 1 0/1 * *")
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

            String dataAsStringFromUrl = HttpClientUtils.doGet(Constant.academyArtUrl);
            downloadImage(dataAsStringFromUrl);
            if(null != dataAsStringFromUrl){
                Document doc = Jsoup.parse(dataAsStringFromUrl);
                // 获取 banner
                String banner = doc.getElementsByClass("banner").toString();
                if(StringUtils.isNoneBlank(banner)){
                    tmpIndexObject.setBanners(urlAddHttp(banner));
//                    String content = doc.getElementsByClass("container").toString();
//                    if(StringUtils.isNoneBlank(content)) {
//                        tmpIndexObject.setContent(urlAddHttp(content));
                        RedisUtil.set(Constant.Redis_Index,JSONObject.toJSONString(tmpIndexObject));
                        return tmpIndexObject;
//                    }
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
        content = content.replaceAll("src=\"./|src=\"/", "src=\"https://www.caamxh.cn/static/");
        content = content.replaceAll("src=\"images", "src=\"https://www.caamxh.cn/static/images");
        content = content.replaceAll("src=\"./images", "src=\"https://www.caamxh.cn/static/images");
        content = content.replaceAll("target=\"_blank\"", "");
        content = content.replaceAll("target=\"blank\"", "");
        content = content.replaceAll(".png", ".png.jpg");

        return content;
    }

    public static String buildIndexJson(){
        JSONObject reslut = new JSONObject();
        try {
            String dataAsStringFromUrl = HttpClientUtils.doGet(Constant.academyArtUrl);
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


    public static void downloadImage(String dataAsStringFromUrl){
        List<String> imageUrl = getImageUrl(dataAsStringFromUrl);
        logger.error("imageUrl ---->" + JSONArray.toJSONString(imageUrl));
        List<String> imageSrc = getImageSrc(imageUrl);
        logger.error("imageSrc ---->" + JSONArray.toJSONString(imageSrc));
        download(imageSrc);
    }

    private static final String IMGURL_REG = "<img[\\n]?(.)*src=(.*?)[^>]*?>";
    //获取ImageUrl地址
    private static List<String> getImageUrl(String html){
        Matcher matcher= Pattern.compile(IMGURL_REG).matcher(html);
        List<String> listimgurl=new ArrayList<String>();
        while (matcher.find()){
            String group = matcher.group();
            group = group.replaceAll("src=\"./|src=\"/", "src=\"https://www.caa.edu.cn/");
            group = group.replaceAll("src=\"images", "src=\"https://www.caa.edu.cn/images");
            group = group.replaceAll("src=\"./images", "src=\"https://www.caa.edu.cn/images");
            listimgurl.add(group);
        }
        return listimgurl;
    }

    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*.[jpg|png|jpeg]";
    //获取ImageSrc地址
    private static List<String> getImageSrc(List<String> listimageurl){
        List<String> listImageSrc=new ArrayList<String>();
        for (String image:listimageurl){
            Matcher matcher=Pattern.compile(IMGSRC_REG).matcher(image);
            if (matcher.find()){
                String group = matcher.group();
                if(group.endsWith("jpg") || group.endsWith("png")|| group.endsWith("jpeg") )
                listImageSrc.add(group);
            }
        }
        return listImageSrc;
    }

    //下载图片
    private  static void download(List<String> listImgSrc) {
        try {
            //开始时间
            Date begindate = new Date();
            for (String url : listImgSrc) {
                // file size
                long size = 0;
                //开始时间
                Date begindate2 = new Date();
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                String path = url.substring(0,url.lastIndexOf("/")).replaceAll("https://www.caa.edu.cn","");
                String finalPath = Constant.imageStaticPath + path;
                File file = new File(finalPath);
                if (!file.exists()) file.mkdirs();

                URL uri = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//                InputStream in = uri.openStream();

                //必须设置false，否则会自动redirect到重定向后的地址
//                conn.setInstanceFollowRedirects(false);
//                conn.connect();
                InputStream in = HttpClientUtils.doGetInput(url);
                String fileName = finalPath + '/' + imageName;
                FileOutputStream fo = new FileOutputStream(new File(fileName));//文件输出流
                byte[] buf = new byte[1024];
                int length = 0;
                logger.info("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                    size += length;
                }
                //关闭流
                in.close();
                fo.close();
                logger.info(imageName + "下载完成");
                //结束时间
                Date overdate2 = new Date();
                double time = overdate2.getTime() - begindate2.getTime();
                logger.info("耗时：" + time / 1000 + "s");
                if(size < 200*1024){
                    Thumbnails.of(fileName).scale(1f).outputFormat("jpg").toFile(fileName);
                }else{
                    Thumbnails.of(fileName).scale(1f).outputQuality( (200*1024f) / size  ).outputFormat("jpg").toFile(fileName);
                }

            }
            Date overdate = new Date();
            double time = overdate.getTime() - begindate.getTime();
            logger.error("总耗时：" + time / 1000 + "s");
        } catch (Exception e) {
            logger.error("下载失败",e);
        }
    }
}
