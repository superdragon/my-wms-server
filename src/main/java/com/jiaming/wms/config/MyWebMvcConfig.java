package com.jiaming.wms.config;

import com.jiaming.wms.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dragon
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                // /** 拦截所有的请求
                .addPathPatterns("/**")
                // 排除一下请求地址不拦截
                .excludePathPatterns("/**/login", "/**.jpg",
                        "/**.ico", "/error", "/captcha/**", "/file/**",
                        "/my_actuator/**", "/statGoods/initData",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**");
        // 如果有多个拦截器，那么可以继续使用 registry.addInterceptor()
    }
}
