package com.malguy.eduservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Configuration
public class FilePathConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/eduservice/teacher/**")//虚拟地址,如1/1.jpg
                .addResourceLocations("file:D:\\blob\\guli_user\\");//       windows
//        .addResourceLocations("file:var/blob/guli_user"); //       linux
    }
}
