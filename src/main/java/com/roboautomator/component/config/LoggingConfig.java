package com.roboautomator.component.config;

import com.roboautomator.component.middleware.LoggingMiddleware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfig implements WebMvcConfigurer {

    private LoggingMiddleware loggingMiddleware;

    public LoggingConfig(LoggingMiddleware loggingMiddleware) {
        this.loggingMiddleware = loggingMiddleware;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingMiddleware);
    }
}