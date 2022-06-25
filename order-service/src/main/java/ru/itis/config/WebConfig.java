package ru.itis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowCredentials(true)
                        .maxAge(3600)
                        .exposedHeaders("Authorization")
                        .allowedMethods("HEAD","POST", "GET", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Accept", "Referer", "Overwrite", "Destination", "Content-Type", "Depth", "User-Agent", "X-File-Size", "X-Requested-With", "If-Modified-Since", "X-File-Name", "Cache-Control", "Origin","Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers");
            }
        };
    }
}
