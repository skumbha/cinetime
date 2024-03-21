package com.cinetime.theater.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class RequestLoggingFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("Request URL: {}", httpRequest.getRequestURL());
        // You can log additional information such as request headers, parameters, etc.

        chain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("Response Time: {} ms", executionTime);
    }

    @Override
    public void destroy() {
        // Cleanup logic, if needed
    }
}