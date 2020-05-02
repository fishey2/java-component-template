package com.roboautomator.component.config;

import com.roboautomator.component.middleware.CorrelationMiddleware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CorrelationConfigTest {

    @Mock
    private CorrelationMiddleware correlationMiddleware;

    @Mock
    private InterceptorRegistry interceptorRegistry;

    private CorrelationConfig correlationConfig;

    @BeforeEach
    void setUpLoggingConfig() {
        correlationConfig = new CorrelationConfig(correlationMiddleware);
    }

    @Test
    void shouldAddCorrelationMiddlewareToInteceptorRegistry() {

        correlationConfig.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry, times(1))
                .addInterceptor(correlationMiddleware);
    }
}
