package com.koombea.techtest.service.impl;

import com.koombea.techtest.constants.Strings;
import com.koombea.techtest.dao.UserDao;
import com.koombea.techtest.model.User;
import com.koombea.techtest.security.utils.JwtManage;
import com.koombea.techtest.security.UserAuthentication;
import com.koombea.techtest.utils.TestStrings;
import com.koombea.techtest.utils.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {
    @Mock
    private UserDao userDao;

    private static JwtManage jwtManage;

    private UserAuthenticationService userAuthenticationService;

    @BeforeAll
    static void beforeAll() {
        jwtManage = new JwtManage(TestStrings.DUMMY_JWT_SECRET);
    }

    @BeforeEach
    void setUp() {
        userAuthenticationService = new UserAuthenticationService(userDao, jwtManage);
    }

    @Test
    void testLoadUserByUsernameShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserAuthentication userAuthentication = (UserAuthentication) userAuthenticationService.loadUserByUsername(user.getEmail());
        assertEquals(user.getEmail(), userAuthentication.getUsername());
        assertEquals(user.getId(), userAuthentication.getId());
    }

    @Test
    void testLoadUserByUsernameShouldFailWhenUserNotExist() {
        User user = UserUtils.getTestUser();
        String email = user.getEmail();
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userAuthenticationService.loadUserByUsername(email));
    }

    @Test
    void testLogInUserShouldSuccessWhenDataCorrect() {
        UserAuthentication userAuthentication = UserUtils.getUserAuthentication();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuthentication, null);
        userAuthenticationService.logInUser(authentication);
        Map<String, Object> map = userAuthenticationService.logInUser(authentication);
        String token =map.get(Strings.ACCESS_TOKEN).toString();
        assertNotEquals("", token);
        assertNotNull(token);
    }
}