package com.roboautomator.component.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.roboautomator.component.util.StringHelper.cleanString;

/**
 * <p>Logging middleware is based on {@link HandlerInterceptorAdapter} and will log the following:{@link HttpServletRequest} before handling.</p>
 * <ul>
 *     <li>{@link #preHandle(HttpServletRequest, HttpServletResponse, Object)} - Logs the {@link HttpServletRequest}</li>
 * </ul>
 */
@Component
public class LoggingMiddleware extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoggingMiddleware.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(cleanString(httpServletRequestToString(request)));
        return true;
    }

    /**
     * <p>Converts the headers of the request ({@link HttpServletRequest#getHeaderNames()} and
     * {@link HttpServletRequest#getHeader(String)}) to a string in the format:</p>
     * <pre>[ key: value, ]</pre>
     *
     * @param request - the {@link HttpServletRequest} intercepted
     *
     * @return a string containing the headers in the request
     */
    private String getAllHeadersAsString(HttpServletRequest request) {


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[ ");

        if(request.getHeaderNames() != null) {
            request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
                stringBuilder.append(headerName);
                stringBuilder.append(": ");
                stringBuilder.append(request.getHeader(headerName));
                stringBuilder.append(", ");
            });
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * <p>Converts the {@link HttpServletRequest} to string format so it can be logged.</p>
     *
     * @param request the {@link HttpServletRequest}
     *
     * @return the extracted information from {@link HttpServletRequest} in String format
     */
    private String httpServletRequestToString(HttpServletRequest request) {
        return request.getClass().getSimpleName() + " {"
                + "type: " + request.getMethod()
                + ", endpoint: " + request.getRequestURI()
                + ", headers: " + getAllHeadersAsString(request)
                + ", contentType: " + request.getContentType()
                + ", contentLength: " + request.getContentLength()
                + "}";
    }
}
