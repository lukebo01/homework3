package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve i file HTML dalla cartella 'all_htmls' come risorse statiche
        registry.addResourceHandler("/all_tables/**")
                .addResourceLocations("file:./all_tables/"); // Percorso relativo alla cartella 'all_htmls'
    }
}