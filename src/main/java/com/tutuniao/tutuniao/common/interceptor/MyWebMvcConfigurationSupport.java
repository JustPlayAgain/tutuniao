package com.tutuniao.tutuniao.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 验证登录拦截器
 */
@Configuration
@Slf4j
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    /**
     * 用户访问拦截
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyHandlerInterceptor())
//                .addPathPatterns("/**") // 添加拦截器规则
//                .excludePathPatterns(Arrays.asList("/*.html","/error","/login/**","/index/**","/img/**","/images/**","/page/**","/js/**","/css/**")); // 哪些访问可以被忽略
//
//        super.addInterceptors(registry);
    }

    /**
     * 修改springboot中默认的静态文件路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //addResourceHandler请求路径
    //addResourceLocations 在项目中的资源路径
    //setCacheControl 设置静态资源缓存时间
//        registry
//            .addResourceHandler("/*.html","/img/**","/images/**","/page/**","/js/**","/css/**")
//            .addResourceLocations("classpath:/static/*.html","classpath:/static/img/","classpath:/static/images/","classpath:/static/page/","classpath:/static/js/","classpath:/static/css/")
//
//            .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
//        super.addResourceHandlers(registry);
    }
}
