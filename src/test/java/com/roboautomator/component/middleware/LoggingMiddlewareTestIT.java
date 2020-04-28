package com.roboautomator.component.middleware;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class LoggingMiddlewareTestIT extends AbstractLoggingTest<LoggingMiddleware> {

    private static final String TEST_METHOD = "POST";
    private static final String TEST_URI = "/health";
    private static final String TEST_CONTENT_TYPE = "application/json";
    private static final int TEST_CONTENT_LENGTH = 5;

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
        setupLoggingAppender(loggingMiddleware);
    }

    @BeforeEach
    public void setupMocks() {
        mockHttpServletRequest();
    }

    @Test
    public void shouldLogRequestWithEmptyArrayWhenNoHeadersAreSpecified() {
        Vector<String> headerNames = new Vector<>();
        doReturn(headerNames.elements()).when(httpServletRequest).getHeaderNames();

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + " {"
                        + "type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "[ ]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + "}");
    }

    @Test
    public void shouldLogRequestWithHeadersArrayWhenNoHeadersAreSpecified() {
        Vector<String> headerNames = new Vector<>();
        headerNames.add("firstHeader");
        headerNames.add("secondHeader");

        doReturn(headerNames.elements()).when(httpServletRequest).getHeaderNames();

        doReturn("value1").when(httpServletRequest).getHeader("firstHeader");
        doReturn("value2").when(httpServletRequest).getHeader("secondHeader");

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + " {"
                        + "type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "["
                        +   " firstHeader: value1, secondHeader: value2, "
                        + "]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + "}");
    }

    private void mockHttpServletRequest() {
        doReturn(TEST_METHOD).when(httpServletRequest).getMethod();
        doReturn(TEST_URI).when(httpServletRequest).getRequestURI();
        doReturn(TEST_CONTENT_TYPE).when(httpServletRequest).getContentType();
        doReturn(TEST_CONTENT_LENGTH).when(httpServletRequest).getContentLength();
    }
}
