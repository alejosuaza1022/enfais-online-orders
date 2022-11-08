package com.enfasis.onlineorders.security.filter;

import com.enfasis.onlineorders.constants.StringExceptions;
import com.enfasis.onlineorders.service.RateLimiterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private RateLimiterService rateLimiterService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int count = rateLimiterService.getApiHitCount(request.getRemoteAddr());
        if (count >= 3) {
            if (Objects.nonNull(response.getOutputStream())) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getOutputStream().print(StringExceptions.TOO_MANY_REQUESTS);
                response.setHeader("Accept-Encoding", "UTF-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
