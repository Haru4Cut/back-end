package com.haru4cut.web;

import com.haru4cut.web.CorsFilter;
import jakarta.servlet.FilterRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public FilterRegistrationBean<CorsFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(corsFilter);

        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
