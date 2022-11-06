package com.enfasis.onlineorders.security.jwtauth;

import com.enfasis.onlineorders.constants.StringExceptions;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.exeption.custom.BadDataException;
import com.enfasis.onlineorders.security.utils.JwtManage;
import com.enfasis.onlineorders.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
public class JwtRequestFilter extends OncePerRequestFilter {
    private UserService userService;
    private JwtManage jwtManage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(Strings.BEARER)) {
            try {
                UsernamePasswordAuthenticationToken authenticationToken =
                        getAuthenticationToken(authorization, request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                if(Objects.nonNull(response.getOutputStream())) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getOutputStream().print(StringExceptions.INVALID_TOKEN);
                    response.setHeader("Accept-Encoding", "UTF-8");
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    protected UsernamePasswordAuthenticationToken getAuthenticationToken(
            String authorization, HttpServletRequest request) {
        Long userId = jwtManage.verifyToken(authorization);
        UsernamePasswordAuthenticationToken authenticationToken = userService.getUsernamePasswordToken(userId);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

}
