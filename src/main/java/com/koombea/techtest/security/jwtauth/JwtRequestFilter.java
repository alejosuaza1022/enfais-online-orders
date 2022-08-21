package com.koombea.techtest.security.jwtauth;

import com.koombea.techtest.constants.StringExceptions;
import com.koombea.techtest.constants.Strings;
import com.koombea.techtest.exeption.custom.BadDataException;
import com.koombea.techtest.security.utils.JwtManage;
import com.koombea.techtest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private  UserService userService;
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
                throw new BadDataException(StringExceptions.JWT_ERROR_TOKEN_VERIFICATION);
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
