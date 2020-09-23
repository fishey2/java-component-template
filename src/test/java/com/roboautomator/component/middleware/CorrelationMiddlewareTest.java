package com.roboautomator.component.middleware;

import java.util.Map;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CorrelationMiddlewareTest extends AbstractLoggingTest<CorrelationMiddleware> {

    private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}$";
    private static final String REQUEST_CORRELATION_ID = "523fbdeb-a905-47e7-b453-42a5a470e43b";
    private static final String CORRELATION_ID = "X-Correlation-Id";
    private static final String LOG_CORRELATION_ID = "correlationId";

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private HandlerMethod handlerMethod;

    @Mock
    private ModelAndView modelAndView;

    private CorrelationMiddleware correlationMiddleware;

    @BeforeEach
    void setupCorrelationMiddleware() {
        correlationMiddleware = new CorrelationMiddleware();
        setupLoggingAppender(correlationMiddleware);
    }

    @Test
    void shouldAddCorrelationIdToResponseIfOneDoesNotExist() {
        var mockServletResponse = new MockHttpServletResponse();

        var result = correlationMiddleware.preHandle(httpServletRequest, mockServletResponse, handlerMethod);

        assertThat(result).isTrue();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isNotNull();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).matches(UUID_PATTERN);
    }

    @Test
    void shouldAddNewCorrelationIdToResponseIfOneIsEmpty() {
        var mockServletResponse = new MockHttpServletResponse();

        var correlationId = "";

        doReturn(correlationId).when(httpServletRequest).getHeader(CORRELATION_ID);

        var result = correlationMiddleware.preHandle(httpServletRequest, mockServletResponse, handlerMethod);

        assertThat(result).isTrue();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isNotNull();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).matches(UUID_PATTERN);
    }

    @Test
    void shouldAddExistingCorrelationIdToResponseIfOneExists() {
        var mockServletResponse = new MockHttpServletResponse();

        doReturn(REQUEST_CORRELATION_ID).when(httpServletRequest).getHeader(CORRELATION_ID);

        var result = correlationMiddleware.preHandle(httpServletRequest, mockServletResponse, handlerMethod);

        assertThat(result).isTrue();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isNotNull();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isEqualTo(REQUEST_CORRELATION_ID);
    }

    @Test
    void shouldAddExistingCorrelationIdToResponseIfOneExistsAndCleanItOfSpecialCharacters() {
        var mockServletResponse = new MockHttpServletResponse();

        doReturn("a\nb\tc\rd").when(httpServletRequest).getHeader(CORRELATION_ID);

        var result = correlationMiddleware.preHandle(httpServletRequest, mockServletResponse, handlerMethod);

        assertThat(result).isTrue();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isNotNull();
        assertThat(mockServletResponse.getHeader(CORRELATION_ID)).isEqualTo("abcd");
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldAddCorrelationIdToLogContext() {
        doReturn(REQUEST_CORRELATION_ID).when(httpServletRequest).getHeader(CORRELATION_ID);

        correlationMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(MDC.get(LOG_CORRELATION_ID)).isEqualTo(REQUEST_CORRELATION_ID);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMDCPropertyMap)
                .contains(Map.of(LOG_CORRELATION_ID, REQUEST_CORRELATION_ID));

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Set logging context correlation ID to \"" + REQUEST_CORRELATION_ID + "\"");
    }

    @Test
    void shouldLogNewlyGeneratedCorrelationIdWhenCreated() {
        correlationMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(MDC.get(LOG_CORRELATION_ID)).matches(UUID_PATTERN);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMessage)
                .contains("No correlationId found, generated new correlationId \"{}\"");
    }

    @Test
    void shouldClearMdcCorrelationIdPropertyFromLoggingContextOnResponse() {
        correlationMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(MDC.get(LOG_CORRELATION_ID)).matches(UUID_PATTERN);

        correlationMiddleware.postHandle(httpServletRequest, httpServletResponse, handlerMethod, modelAndView);

        assertThat(MDC.get(LOG_CORRELATION_ID)).isNull();
    }

    @Test
    void shouldLogThatItIsClearingTheCorrelationIdOnPostHandle() {
        correlationMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);
        correlationMiddleware.postHandle(httpServletRequest, httpServletResponse, handlerMethod, modelAndView);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Clearing logging context correlation ID");
    }
}
