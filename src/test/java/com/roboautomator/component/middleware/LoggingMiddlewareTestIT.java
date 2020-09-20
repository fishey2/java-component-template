package com.roboautomator.component.middleware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoggingMiddlewareTestIT {

    @MockBean
    private LoggingMiddleware loggingMiddleware;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkLoggingMiddlewarePreHandlerIsCalledOnRequest() throws Exception {

        doCallRealMethod().when(loggingMiddleware).preHandle(any(), any(), any());

        mockMvc.perform(get("/health"));

        verify(loggingMiddleware, times(1))
                .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any());
    }

    @Test
    void checkLoggingMiddlewarePostHandlerIsCalledOnResponse() throws Exception {

        doCallRealMethod().when(loggingMiddleware).preHandle(any(), any(), any());
        doCallRealMethod().when(loggingMiddleware).postHandle(any(), any(), any(), any());

        mockMvc.perform(get("/health"));

        verify(loggingMiddleware, times(1))
                .postHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(), any());
    }
}