package com.roboautomator.component.config.logging;

import com.roboautomator.component.middleware.LoggingMiddleware;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

    private LoggingMiddleware loggingMiddleware;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingMiddleware);
    }
}