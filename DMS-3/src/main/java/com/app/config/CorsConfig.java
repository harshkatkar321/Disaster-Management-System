package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
        .allowedOrigins("http://192.168.1.14:5173") // adjust origin
        .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
//      http://192.168.1.14:3000
    }
}
