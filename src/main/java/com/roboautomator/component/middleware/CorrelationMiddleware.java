package com.roboautomator.component.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CorrelationMiddleware extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(CorrelationMiddleware.class);

    private static final String X_CORRELATION_ID = "X-Correlation-Id";
    private static final String LOG_CORRELATION_ID = "correlationId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var correlationId = extractOrGenerateCorrelationId(request);
        response.setHeader(X_CORRELATION_ID, correlationId);
        MDC.put(LOG_CORRELATION_ID, correlationId);

        log.info("Set logging context correlation ID to \"{}\"", correlationId);

        return true;
    }

    private String extractOrGenerateCorrelationId(HttpServletRequest request) {
        return (request.getHeader(X_CORRELATION_ID) == null || request.getHeader(X_CORRELATION_ID).isEmpty())
                ? generateCorrelationId()
                : request.getHeader(X_CORRELATION_ID);
    }

    private String generateCorrelationId() {
        var generateCorrelationID = UUID.randomUUID().toString();

        log.info("No correlationId found, generated new correlationId \"{}\"", generateCorrelationID);

        return generateCorrelationID;
    }
}
