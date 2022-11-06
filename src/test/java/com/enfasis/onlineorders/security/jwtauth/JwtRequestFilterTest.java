package com.enfasis.onlineorders.security.jwtauth;

import com.enfasis.onlineorders.exeption.custom.BadDataException;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.service.impl.UserServiceImpl;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.security.utils.JwtManage;
import com.enfasis.onlineorders.utils.TestStrings;
import com.enfasis.onlineorders.utils.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {
    @Mock
    private UserServiceImpl userService;

    private static JwtManage jwtManage;

    @BeforeAll
    static void beforeAll() {
        jwtManage = new JwtManage(TestStrings.DUMMY_JWT_SECRET);
    }

    @BeforeEach
    void setUp() {
        jwtRequestFilter = new JwtRequestFilter(userService, jwtManage );
    }

    private JwtRequestFilter jwtRequestFilter;

    @Test
    void testDoFilterJWTInternalShouldFailWhenAuthorizationNull() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        JwtRequestFilter jwtRequestFilter1 = Mockito.spy(jwtRequestFilter);
        verify(jwtRequestFilter1, never()).getAuthenticationToken(any(), any());
        jwtRequestFilter1.doFilterInternal(request, response, filterChain);
        verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterJWTInternalShouldFailWhenAuthorizationNotBearer() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        JwtRequestFilter jwtRequestFilter1 = Mockito.spy(jwtRequestFilter);
        when(request.getHeader(Strings.AUTHORIZATION_BODY)).thenReturn("random");
        jwtRequestFilter1.doFilterInternal(request, response, filterChain);
        verify(jwtRequestFilter1, never()).getAuthenticationToken(any(), any());
        verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterJWTInternalShouldSuccessWhenDataCorrect() throws ServletException, IOException {
        User user = UserUtils.getTestUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        JwtRequestFilter jwtRequestFilter1 = Mockito.spy(jwtRequestFilter);
        when(request.getHeader(Strings.AUTHORIZATION_BODY)).thenReturn(TestStrings.BEARER_TEST);
        doReturn(new UsernamePasswordAuthenticationToken(user, "")).when(jwtRequestFilter1)
                .getAuthenticationToken(TestStrings.BEARER_TEST, request);
        jwtRequestFilter1.doFilterInternal(request, response, filterChain);
        verify(filterChain, Mockito.times(1)).doFilter(request, response);
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals(user.getId(), user1.getId());
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterJWTInternalShouldFailWhenNotValidToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        JwtRequestFilter jwtRequestFilter1 = Mockito.spy(jwtRequestFilter);
        when(request.getHeader(Strings.AUTHORIZATION_BODY)).thenReturn(TestStrings.BEARER_TEST);
        Mockito.doThrow(new AccessDeniedException("")).when(jwtRequestFilter1)
                .getAuthenticationToken(any(), any());
        assertThrows(BadDataException.class, () ->
                jwtRequestFilter1.doFilterInternal(request, response, filterChain));
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testGetAuthenticationTokenShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        String token = jwtManage.generateToken(user.getId(), user.getEmail());
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        when(userService.getUsernamePasswordToken(any(Long.class))).
                thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        HttpServletRequest request = mock(HttpServletRequest.class);
        jwtRequestFilter.getAuthenticationToken(Strings.BEARER + token, request);
        verify(userService).getUsernamePasswordToken(idCaptor.capture());
        assertEquals(user.getId(), idCaptor.getValue());
    }
}