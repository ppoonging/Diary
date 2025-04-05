package com.diary;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/css/**")
                .addResourceLocations(
                        "classpath:/static/css/",
                        "file:/home/ubuntu/diary1/static/css/"
                );

        registry.addResourceHandler("/js/**")
                .addResourceLocations(
                        "classpath:/static/js/",
                        "file:/home/ubuntu/diary1/static/js/"
                );

        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "classpath:/static/images/",
                        "file:/home/ubuntu/diary1/static/images/"
                );
    }
}
