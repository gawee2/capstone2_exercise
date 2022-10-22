package com.example.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private String connectPath = "/imagePath/**";
    private String resourcePath = "file:///Users/duskite/downloads/img/";
//    private String resourcePath = "file:///home/ec2-user/downloads/img/";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler(connectPath).addResourceLocations(resourcePath);

    }

}
