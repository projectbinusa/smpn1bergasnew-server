package com.smpn1.bergas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// membuat configurasi cors origin mapping controller
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("X-Requested-With", "Content-Type", "Origin", "Authorization", "Accept", "Client-Security-Token", "Accept-Encoding")
                .allowCredentials(false)
            //    ini yang buat fe local
                .allowedOrigins("http://localhost:3000")
            //    ini yang buat fe deploy
               .allowedOrigins("https://smpn1bergas.sch.id")
                .maxAge(3600);
    }
}
