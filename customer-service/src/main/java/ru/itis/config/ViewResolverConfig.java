package ru.itis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class ViewResolverConfig {
    @Bean
    public ViewResolver getViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(false);
        resolver.setSuffix(".ftl");

        return resolver;
    }
}
