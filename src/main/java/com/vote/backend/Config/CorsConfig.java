package com.vote.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**Springboot跨域请求配置类*/
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                //允许跨域的域名
                .allowedOrigins("*")
                //允许cookie
                .allowCredentials(true)
                //允许跨域请求的方式
                .allowedMethods("GET","POST","DELETE","PUT")
                //允许跨域的Header
                .allowedHeaders("*")
                //允许跨域的时间
                .maxAge(3600);
    }
}
