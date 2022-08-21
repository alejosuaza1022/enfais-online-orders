package com.koombea.techtest.security.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koombea.techtest.constants.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            Map<String, String> dataResponse = Map.of(Strings.MESSAGE_RESPONSE, authException.getMessage());
            OBJECT_MAPPER.writeValue(response.getOutputStream(), dataResponse);
        }
    }


