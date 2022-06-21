package com.jiaming.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author dragon
 */
@Configuration
public class MyCorsFilter {
    private CorsConfiguration corsConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许跨域时，前端可以获取的自定义响应头信息
        corsConfiguration.addExposedHeader("captcha_key");
        // 允许跨域
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setMaxAge(7200L);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfig());
        return new CorsFilter(source);
    }
}
