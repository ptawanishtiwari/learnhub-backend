package com.example.parth.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path videoPath = Paths.get("uploads/lectures");

        String videoLocation =
                videoPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/videos/lectures/**")
                .addResourceLocations(
                        "file:" + videoLocation + "/"
                );
    }
}