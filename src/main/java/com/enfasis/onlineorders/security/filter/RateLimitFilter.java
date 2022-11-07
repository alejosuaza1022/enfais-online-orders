package com.enfasis.onlineorders.security.filter;

import com.enfasis.onlineorders.service.RateLimiterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private RateLimiterService rateLimiterService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        rateLimiterService.getApiHitCount(request.getRemoteAddr());
        rateLimiterService.incrementApiHitCount(request.getRemoteAddr());
        filterChain.doFilter(request, response);
    }
}
