package com.jingxu.shopdemo.config;

import com.jingxu.shopdemo.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/order/**",
                        "/cart/**",
                        "/ordered/**",
                        "/admin/add",
                        "/admin/delivery",
                        "/admin/query") // 需要登录的路径
                .excludePathPatterns("/user","/order/queryAll","/admin/login"); // 不需要登录的路径
    }
}
