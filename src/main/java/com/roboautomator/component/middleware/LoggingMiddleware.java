package com.roboautomator.component.middleware;

import static com.roboautomator.component.util.StringHelper.cleanString;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Logging middleware is based on {@link HandlerInterceptorAdapter} and will log the following:{@link HttpServletRequest} before handling.</p>
 * <ul>
 *     <li>{@link #preHandle(HttpServletRequest, HttpServletResponse, Object)} - Logs the {@link HttpServletRequest}</li>
 * </ul>
 */
@Slf4j
@Component
public class LoggingMiddleware extends HandlerInterceptorAdapter {

    private static final String LOG_TEMPLATE = "{}: {}";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info(LOG_TEMPLATE, request.getClass().getSimpleName(),
            cleanString(httpServletRequestToString(request)));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView view) {

        log.info(LOG_TEMPLATE, response.getClass().getSimpleName(),
            cleanString(httpServletResponseToString(response, request)));
    }

    /**
     * <p>Converts the headers of the request ({@link HttpServletRequest#getHeaderNames()} and
     * {@link HttpServletRequest#getHeader(String)}) to a string in the format:</p>
     * <pre>[ key: value, ]</pre>
     *
     * @param request - the {@link HttpServletRequest} intercepted
     * @return a string containing the headers in the request
     */
    private String getAllHeadersAsString(HttpServletRequest request) {


        var stringBuilder = new StringBuilder();

        stringBuilder.append("[ ");

        if (request.getHeaderNames() != null) {
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

    private String getAllResponseHeadersAsString(HttpServletResponse response) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[ ");

        if (response.getHeaderNames() != null) {

            response.getHeaderNames().forEach(headerName -> {
                stringBuilder.append(headerName);
                stringBuilder.append(": ");
                stringBuilder.append(response.getHeader(headerName));
                stringBuilder.append(", ");
            });
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * <p>Converts the {@link HttpServletResponse} to string format so it can be logged.</p>
     *
     * @param response the {@link HttpServletResponse}
     * @return the extracted information from {@link HttpServletResponse} in String format
     */
    private String httpServletResponseToString(HttpServletResponse response, HttpServletRequest request) {
        return "{"
            + " method: " + request.getMethod()
            + ", endpoint: " + request.getRequestURI()
            + ", status: " + response.getStatus()
            + ", headers: " + getAllResponseHeadersAsString(response)
            + " }";
    }

    /**
     * <p>Converts the {@link HttpServletRequest} to string format so it can be logged.</p>
     *
     * @param request the {@link HttpServletRequest}
     * @return the extracted information from {@link HttpServletRequest} in String format
     */
    private String httpServletRequestToString(HttpServletRequest request) {
        return "{"
            + " type: " + request.getMethod()
            + ", endpoint: " + request.getRequestURI()
            + ", headers: " + getAllHeadersAsString(request)
            + ", contentType: " + request.getContentType()
            + ", contentLength: " + request.getContentLength()
            + " }";
    }
}
