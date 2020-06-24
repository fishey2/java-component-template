package com.roboautomator.component.config.logging;

import com.roboautomator.component.middleware.CorrelationMiddleware;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class CorrelationConfig implements WebMvcConfigurer {

    private CorrelationMiddleware correlationMiddleware;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationMiddleware);
    }
}
