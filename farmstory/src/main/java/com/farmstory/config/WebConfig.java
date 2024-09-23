package com.farmstory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/MainImage/**")
                 // 윈도우 경로
                 .addResourceLocations("file:///C:/Users/lotte4/Desktop/Spring/uploads/MainImage/");
                // 맥 경로
               // .addResourceLocations("file:///Users/hwangsubin/Desktop/farm/uploads/MainImage/");
    }
}