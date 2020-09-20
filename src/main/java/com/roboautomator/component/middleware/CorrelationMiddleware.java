package com.roboautomator.component.middleware;

import static com.roboautomator.component.util.StringHelper.cleanString;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
public class CorrelationMiddleware extends HandlerInterceptorAdapter {

    private static final String X_CORRELATION_ID = "X-Correlation-Id";
    private static final String LOG_CORRELATION_ID = "correlationId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var correlationId = cleanString(extractOrGenerateCorrelationId(request));
        response.setHeader(X_CORRELATION_ID, correlationId);
        MDC.put(LOG_CORRELATION_ID, correlationId);

        log.info("Set logging context correlation ID to \"{}\"", correlationId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        log.info("Clearing logging context correlation ID");
        MDC.remove(LOG_CORRELATION_ID);
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
