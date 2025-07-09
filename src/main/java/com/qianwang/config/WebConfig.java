package com.qianwang.config;

import com.qianwang.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/visitor/user/**")
                .excludePathPatterns("/visitor/user/login")
                .excludePathPatterns("/visitor/user/register")
                .excludePathPatterns("/visitor/user/logout"); // 可选：退出登录接口无需拦截
    }
}
