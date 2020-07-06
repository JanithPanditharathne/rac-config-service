package com.zone24x7.rac.configservice.util;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.zone24x7.rac.configservice.util.Strings.METHOD;
import static com.zone24x7.rac.configservice.util.Strings.ORIGIN;
import static com.zone24x7.rac.configservice.util.Strings.REQUEST_ID;
import static com.zone24x7.rac.configservice.util.Strings.URI;
import static com.zone24x7.rac.configservice.util.Strings.USER;

@Component
public class CSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Add keys to mdc.
        String uuid = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, uuid);
        MDC.put(METHOD, request.getMethod());
        MDC.put(URI, request.getServletPath());
        MDC.put(ORIGIN, request.getServerName());
        MDC.put(USER, request.getHeader("User-ID"));

        // Set uuid to response header for tracking purposes.
        response.setHeader(REQUEST_ID, uuid);

        try {
            filterChain.doFilter(request, response);
        } finally {

            // Remove keys from mdc.
            MDC.remove(REQUEST_ID);
            MDC.remove(METHOD);
            MDC.remove(URI);
            MDC.remove(ORIGIN);
            MDC.remove(USER);
        }
    }

}
