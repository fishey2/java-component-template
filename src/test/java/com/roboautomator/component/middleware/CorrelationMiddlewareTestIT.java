package com.roboautomator.component.middleware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorrelationMiddlewareTestIT {

    @MockBean
    private CorrelationMiddleware correlationMiddleware;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkCorrelationMiddlewarePreHandlerIsCalledOnRequest() throws Exception {

        doCallRealMethod().when(correlationMiddleware).preHandle(any(), any(), any());

        mockMvc.perform(get("/health"));

        verify(correlationMiddleware, times(1))
                .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any());
    }

    @Test
    void verifyThatCorrelationIdIsAddedToResponse() throws Exception {

        doCallRealMethod().when(correlationMiddleware).preHandle(any(), any(), any());

        var responseEntity = mockMvc.perform(get("/health"))
                .andReturn();

        assertThat(responseEntity.getResponse().getHeader("X-Correlation-Id")).isNotNull();
    }

    @Test
    void verifyThatCorrelationIdIsAddedToResponseFromRequest() throws Exception {
        String correlationId = "Correlation-ID";

        doCallRealMethod().when(correlationMiddleware).preHandle(any(), any(), any());

        var responseEntity = mockMvc.perform(get("/health").header("X-Correlation-Id", correlationId))
                .andReturn();

        assertThat(responseEntity.getResponse().getHeader("X-Correlation-Id")).isEqualTo(correlationId);
    }
}
