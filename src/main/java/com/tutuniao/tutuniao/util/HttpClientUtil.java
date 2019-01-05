package com.tutuniao.tutuniao.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author charles.chengc
 */
public class HttpClientUtil {

    private static Logger logger                = LoggerFactory.getLogger(HttpClientUtil.class);

    private final static String                       DEFAULT_CHARSETNAME   = "UTF-8";
    // 椎名修改，改为20s，应对延迟较高的请求
    private final static int                          DEFAULT_TIMEOUT       = 60000;
    // 椎名修改，改为20s，应对延迟较高的请求
    private final static int                          CONNECTION_TIMEOUT    = 30000;

    private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();

    static {
        httpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(8);
        httpConnectionManager.getParams().setMaxTotalConnections(100);
        // httpConnectionManager.getParams().setConnectionTimeout(3000);
        // httpConnectionManager.getParams().setSoTimeout(5000);
        httpConnectionManager.getParams().setTcpNoDelay(true);
        // 这里的单位是秒
        httpConnectionManager.getParams().setLinger(1);

        // 如果发生错误则连续尝试1次
        httpConnectionManager.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                                                       new DefaultHttpMethodRetryHandler(1, false));

        // 处理无证书的https
        Protocol myhttps = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
    }

    /**
     * 根据url获取ResponseBody,method=get
     * 
     * @param url exp:http://192.168.1.1:8080/dir/target.html
     * @return 以byte[]的方式放回
     */
    private static byte[] getDataFromUrl(String url, int timeout) {
        if (StringUtils.isBlank(url)) {
            logger.error("url is blank!");
            return null;
        }
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        // 连接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        // 等待数据返回超时
        httpClient.getParams().setSoTimeout(timeout);
        GetMethod method = new GetMethod(url);

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getResponseBody();
            } else {
                throw new RuntimeException("http request error,return code:" + statusCode + ",msg:"
                                           + new String(method.getResponseBody()));
            }
        } catch (HttpException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return null;
    }

    /**
     * 探测url是否正常,返回200 ok表示正常
     * 
     * @param url
     * @return
     */
    public static boolean testUrlIsOk(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        HttpClient httpClient = new HttpClient();
        // 连接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
        // 等待数据返回超时
        httpClient.getParams().setSoTimeout(2000);
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, false));
        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return true;
            }
        } catch (HttpException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return false;
    }

    /**
     * 根据url获取ResponseBody,method=get
     * 
     * @param url exp:http://192.168.1.1:8080/dir/target.html
     * @param charsetName exp:http://192.168.1.1:8080/dir/target.html
     * @param timeout
     * @return 以String的方式返回
     * @throws UnsupportedEncodingException
     */
    public static String getDataAsStringFromUrl(String url, String charsetName, int timeout) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(url)) {
            logger.error("url is blank!");
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
            charsetName = DEFAULT_CHARSETNAME;
        }
        byte[] responseBody = getDataFromUrl(url, timeout);
        if (null != responseBody) {
            try {
                return new String(responseBody, charsetName);
            } catch (UnsupportedEncodingException e) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 加入head参数的Get方式
     * @param url
     * @param charsetName
     * @param timeout
     * @param headerParams
     * @return
     */
    public static String getDataAsStringFromUrlWithHeader(String url, String charsetName, int timeout, Map<String, String> headerParams) {
        if (StringUtils.isBlank(url)) {
            logger.error("url is blank!");
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
            charsetName = DEFAULT_CHARSETNAME;
        }
        byte[] responseBody = getDataFromUrl(url, timeout, headerParams);
        if (null != responseBody) {
            try {
                return new String(responseBody, charsetName);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 加入header的post请求
     * @param url
     * @param map
     * @param headerParams
     * @return
     * @throws IOException
     */
    public static String getDataAsStringFromPostUrlWithHeader(String url, Map<String, String> map, Map<String, String> headerParams) throws IOException {
        return getDataAsStringFromPostUrl(url, map, headerParams, DEFAULT_TIMEOUT);

    }


    private static String getDataAsStringFromPostUrl(String url, Map<String, String> map, Map<String, String> headerParams, int timeout) throws IOException {
        Assert.hasText(url, "url can not be blank!");
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        // 连接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_TIMEOUT);
        // 等待数据返回超时
        httpClient.getParams().setSoTimeout(timeout);
        PostMethod method = new PostMethod(url);
        // 默认是utf8,可以用参数httpMethodParams更改
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARSETNAME);

        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                method.setParameter(entry.getKey(), entry.getValue());
            }
        }

        //加入header
        for(Map.Entry<String, String> entry : headerParams.entrySet()) {
            method.addRequestHeader(new Header(entry.getKey(), entry.getValue()));
        }

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return new String(method.getResponseBody(), DEFAULT_CHARSETNAME);
            } else {
                throw new RuntimeException("http request error,return code:" + statusCode + ",msg:"
                        + new String(method.getResponseBody()));
            }
        } catch (HttpException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }


    private static byte[] getDataFromUrl(String url, int timeout, Map<String, String> headerParams) {
        if (StringUtils.isBlank(url)) {
            logger.error("url is blank!");
            return null;
        }
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        // 连接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        // 等待数据返回超时
        httpClient.getParams().setSoTimeout(timeout);
        GetMethod method = new GetMethod(url);
        //加入header
        for(Map.Entry<String, String> entry : headerParams.entrySet()) {
            method.addRequestHeader(new Header(entry.getKey(), entry.getValue()));
        }

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return method.getResponseBody();
            } else {
                throw new RuntimeException("http request error,return code:" + statusCode + ",msg:"
                        + new String(method.getResponseBody()));
            }
        } catch (HttpException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
        return null;

    }

    /**
     * 根据url获取ResponseBody,method=get
     *
     * @param url exp:http://192.168.1.1:8080/dir/target.html
     * @param charsetName 默认使用UTF-8编码
     * @return 以String的方式返回
     * @throws UnsupportedEncodingException
     */
    public static String getDataAsStringFromUrl(String url, String charsetName) throws UnsupportedEncodingException {
        return getDataAsStringFromUrl(url, charsetName, DEFAULT_TIMEOUT);
    }

    public static String getDataAsStringFromUrl(String url) throws UnsupportedEncodingException {
        return getDataAsStringFromUrl(url, null, DEFAULT_TIMEOUT);
    }

    /**
     * 根据url获取ResponseBody,method=post
     */
    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map) throws IOException {
        return getDataAsStringFromPostUrl(url, map, DEFAULT_TIMEOUT);

    }

    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map, int timeout) throws IOException {
        return getDataAsStringFromPostUrl(url, map, timeout, null, DEFAULT_CHARSETNAME);
    }

    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map, String charset) throws IOException {
        return getDataAsStringFromPostUrl(url, map, DEFAULT_TIMEOUT, null, charset);
    }


    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map, int timeout, String charset) throws IOException {
        return getDataAsStringFromPostUrl(url, map, timeout, null, charset);
    }

    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map, int timeout,
                                                    Map<String, Object> httpMethodParams) throws IOException {
        return getDataAsStringFromPostUrl(url, map, timeout, httpMethodParams, DEFAULT_CHARSETNAME);
    }

    public static String getDataAsStringFromPostUrl(String url, Map<String, String> map, int timeout,
                                                    Map<String, Object> httpMethodParams, String charset) throws IOException {
        Assert.hasText(url, "url can not be blank!");
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        // 连接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_TIMEOUT);
        // 等待数据返回超时
        httpClient.getParams().setSoTimeout(timeout);
        PostMethod method = new PostMethod(url);
        // 默认是utf8,可以用参数httpMethodParams更改
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

        if (null != httpMethodParams) {
            for (Map.Entry<String, Object> entry : httpMethodParams.entrySet()) {
                method.getParams().setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                method.setParameter(entry.getKey(), entry.getValue());
            }
        }

        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                return new String(method.getResponseBody(), DEFAULT_CHARSETNAME);
            } else {
                throw new RuntimeException("http request error,return code:" + statusCode + ",msg:"
                                           + new String(method.getResponseBody()));
            }
        } catch (HttpException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }

    public static String assemble(String url, Map<String, Object> params) {
        if (url == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(params)) {
            return url;
        }
        int len = url.length();
        char end = ((len == 0 ? ' ' : url.charAt(len - 1)));
        String separator = "";
        if (end != '?' && end != '&') {
            separator = StringUtils.contains(url, '?') ? "&" : "?";
        }
        StringBuilder builder = new StringBuilder(url);
        Set<Map.Entry<String, Object>> set = params.entrySet();
        Map.Entry<String, Object> entry;
        for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
            try {
                entry = it.next();
                builder.append(separator).append(entry.getKey()).append('=').append(URLEncoder.encode(String.valueOf(entry.getValue()),
                                                                                                      "UTF-8"));
                separator = "&";
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return builder.toString();
    }

}

