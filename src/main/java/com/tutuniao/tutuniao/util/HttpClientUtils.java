package com.tutuniao.tutuniao.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtils {

    private static HttpClientUtils httpClientUtil;
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    public static HttpClientUtils getInstance() {
        if (httpClientUtil == null) {
            httpClientUtil = new HttpClientUtils();
        }
        return httpClientUtil;
    }

    public static String doGet(String url){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"utf-8");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static InputStream doGetInput(String url){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        InputStream result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = resEntity.getContent();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static String get(String  url) {
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String srtResult = null;
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity(),"utf-8");//获得返回的结果
            }
        } catch (Exception e) {
            logger.error("get请求异常",e);
        } finally {
            try {
                httpCilent.close();
            } catch (Exception e) {
                logger.error("httpCilent关闭异常",e);
            }
        }
        return srtResult;
    }


    public static String post(String url, Map<String, String> params, Map<String, String>  headers) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String body = null;

        HttpPost post = postForm(url, params, headers);
        body = invoke(httpClient, post);
        httpClient.getConnectionManager().shutdown();

        return body;

    }
	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);

        return paseResponse(response);
    }
	private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error("post返回转换字符串异常",e);
        }  
          
        return body;  
    }  
	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (Exception e) {
            logger.error("post请求异常",e);
        }  
        return response;  
    }  
	private static HttpPost postForm(String url, Map<String, String> params, Map<String, String> headers){
        
        HttpPost httpost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }  
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (Exception e) {
            logger.error("post请求添加参数异常",e);
        }  
          
        return httpost;  
    }  
}
