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

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class LoggingMiddlewareTest  extends AbstractLoggingTest<LoggingMiddleware> {

    private static final String TEST_METHOD = "POST";
    private static final String TEST_URI = "/health";
    private static final String TEST_CONTENT_TYPE = "application/json";

    private static final int TEST_CONTENT_LENGTH = 5;
    private static final int TEST_STATUS_CODE = 200;

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

    @Test
    public void shouldReturnTrueWhenPreHandleIsCalled() {
        assertThat(loggingMiddleware.preHandle(httpServletRequest,
                httpServletResponse,
                handlerMethod)).isTrue();
    }

    @Test
    public void shouldLogRequestWithEmptyArrayWhenNoHeadersAreSpecified() {
        mockHttpServletRequest();

        doReturn(TEST_CONTENT_TYPE).when(httpServletRequest).getContentType();
        doReturn(TEST_CONTENT_LENGTH).when(httpServletRequest).getContentLength();

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + ": {"
                        + " type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "[ ]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + " }");
    }

    @Test
    public void shouldCleanStringForAnyInputsFromHttpServletRequest() {
        mockHttpServletRequest();

        doReturn(TEST_CONTENT_TYPE).when(httpServletRequest).getContentType();
        doReturn(TEST_CONTENT_LENGTH).when(httpServletRequest).getContentLength();

        Vector<String> headerNames = new Vector<>();
        headerNames.add("cleanString");

        doReturn(headerNames.elements()).when(httpServletRequest).getHeaderNames();

        doReturn("val\n\r\tue1").when(httpServletRequest).getHeader("cleanString");

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + ": {"
                        + " type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "[ cleanString: value1, ]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + " }");
    }

    @Test
    public void shouldLogRequestWithEmptyArrayWithEmptyHeadersAreSpecified() {
        mockHttpServletRequest();

        doReturn(TEST_CONTENT_TYPE).when(httpServletRequest).getContentType();
        doReturn(TEST_CONTENT_LENGTH).when(httpServletRequest).getContentLength();

        Vector<String> headerNames = new Vector<>();
        doReturn(headerNames.elements()).when(httpServletRequest).getHeaderNames();

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + ": {"
                        + " type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "[ ]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + " }");
    }

    @Test
    public void shouldLogRequestWithHeadersArrayWhenNoHeadersAreSpecified() {
        mockHttpServletRequest();

        doReturn(TEST_CONTENT_TYPE).when(httpServletRequest).getContentType();
        doReturn(TEST_CONTENT_LENGTH).when(httpServletRequest).getContentLength();

        var headerNamesVector = new Vector<String>();
        headerNamesVector.add("firstHeader");
        headerNamesVector.add("secondHeader");

        doReturn(headerNamesVector.elements()).when(httpServletRequest).getHeaderNames();

        doReturn("value1").when(httpServletRequest).getHeader("firstHeader");
        doReturn("value2").when(httpServletRequest).getHeader("secondHeader");

        loggingMiddleware.preHandle(httpServletRequest, httpServletResponse, handlerMethod);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletRequest.getClass().getSimpleName() + ": {"
                        + " type: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", headers: " + "["
                        +   " firstHeader: value1, secondHeader: value2, "
                        + "]"
                        + ", contentType: " + TEST_CONTENT_TYPE
                        + ", contentLength: " + TEST_CONTENT_LENGTH
                        + " }");
    }

    @Test
    public void shouldLogResponseStatusAndHeadersAsEmptyListIfNull() {
        mockHttpServletRequest();
        mockHttpServletResponse();

        doReturn(null).when(httpServletResponse).getHeaderNames();

        loggingMiddleware.postHandle(httpServletRequest, httpServletResponse, handlerMethod, null);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletResponse.getClass().getSimpleName() + ": {"
                        + " method: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", status: " + TEST_STATUS_CODE
                        + ", headers: [ ]"
                        + " }");
    }

    @Test
    public void shouldLogResponseStatusAndHeadersAsEmptyListIfHeaderNamesIsEmpty() {
        mockHttpServletRequest();
        mockHttpServletResponse();

        doReturn(Collections.EMPTY_LIST).when(httpServletResponse).getHeaderNames();

        loggingMiddleware.postHandle(httpServletRequest, httpServletResponse, handlerMethod, null);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletResponse.getClass().getSimpleName() + ": {"
                        + " method: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", status: " + TEST_STATUS_CODE
                        + ", headers: [ ]"
                        + " }");
    }

    @Test
    public void shouldLogResponseStatusAndHeaders() {
        mockHttpServletRequest();
        mockHttpServletResponse();

        doReturn(List.of("firstHeader", "secondHeader")).when(httpServletResponse).getHeaderNames();

        doReturn("value1").when(httpServletResponse).getHeader("firstHeader");
        doReturn("value2").when(httpServletResponse).getHeader("secondHeader");

        loggingMiddleware.postHandle(httpServletRequest, httpServletResponse, handlerMethod, null);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains(httpServletResponse.getClass().getSimpleName() + ": {"
                        + " method: " + TEST_METHOD
                        + ", endpoint: " + TEST_URI
                        + ", status: " + TEST_STATUS_CODE
                        + ", headers: " + "["
                        +   " firstHeader: value1, secondHeader: value2, "
                        + "]"
                        + " }");
    }

    private void mockHttpServletResponse() {
        doReturn(TEST_STATUS_CODE).when(httpServletResponse).getStatus();
    }

    private void mockHttpServletRequest() {
        doReturn(TEST_METHOD).when(httpServletRequest).getMethod();
        doReturn(TEST_URI).when(httpServletRequest).getRequestURI();
    }
}