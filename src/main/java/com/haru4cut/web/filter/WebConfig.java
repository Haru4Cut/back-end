package com.haru4cut.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedMethods("POST, GET, OPTIONS, DELETE, PUT, PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600L);

    }

}
