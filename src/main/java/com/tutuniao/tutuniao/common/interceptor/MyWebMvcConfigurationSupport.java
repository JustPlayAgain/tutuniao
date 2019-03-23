package com.tutuniao.tutuniao.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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

        System.out.println("pppppppppppppp");
        registry.addInterceptor(new MyHandlerInterceptor())
                .addPathPatterns("/**") // 添加拦截器规则
                .excludePathPatterns("/tutuniao/login"); // 哪些访问可以被忽略
        super.addInterceptors(registry);
    }
}
