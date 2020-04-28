package com.roboautomator.component.middleware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LoggingMiddlewareTest  {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private HandlerMethod handlerMethod;

    private LoggingMiddleware loggingMiddleware;

    @BeforeEach
    public void setupLoggingMiddleware() {
        loggingMiddleware = new LoggingMiddleware();
    }

    @Test
    public void shouldReturnTrueWhenPreHandleIsCalled() {
        assertThat(loggingMiddleware.preHandle(httpServletRequest,
                httpServletResponse,
                handlerMethod)).isTrue();
    }
}