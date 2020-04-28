package com.roboautomator.component.config;

import com.roboautomator.component.middleware.LoggingMiddleware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoggingMiddlewareTest {

    @Mock
    private LoggingMiddleware loggingMiddleware;

    @Mock
    private InterceptorRegistry interceptorRegistry;

    private LoggingConfig loggingConfig;

    @BeforeEach
    public void setUpLoggingConfig() {
        loggingConfig = new LoggingConfig(loggingMiddleware);
    }

    @Test
    public void shouldAddLoggingMiddlewareToInteceptorRegistry() {

        loggingConfig.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry, times(1))
                .addInterceptor(loggingMiddleware);
    }
}
