package com.koombea.techtest.service.impl;

import com.koombea.techtest.constants.Strings;
import com.koombea.techtest.dao.UserDao;
import com.koombea.techtest.dto.UserCreated;
import com.koombea.techtest.dto.UserPrincipalSecurity;
import com.koombea.techtest.exeption.custom.UniqueConstraintViolationException;
import com.koombea.techtest.model.User;
import com.koombea.techtest.payload.UserPayload;
import com.koombea.techtest.utils.TestStrings;
import com.koombea.techtest.utils.UserUtils;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void saveShouldFailedForEmailDuplicated() {
        when(userDao.findByEmail(TestStrings.TEST_USER_EMAIL)).thenReturn(Optional.of(UserUtils.getTestUser()));
        UserPayload userPayload = UserUtils.getTestUserCreatePayload();
        assertThrows(UniqueConstraintViolationException.class, () -> userService.save(userPayload));
    }

    @Test
    void saveShouldBeSuccessfully() {
        UserPayload userPayload = UserUtils.getTestUserCreatePayload();
        User user = UserUtils.getTestUser();
        when(userDao.findByEmail(TestStrings.TEST_USER_EMAIL)).thenReturn(Optional.empty());
        when(modelMapper.map(userPayload, User.class)).thenReturn(user);
        when(userDao.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserCreated.class)).thenReturn(UserUtils.getTestUserCreated());
        UserCreated userCreated = userService.save(userPayload);
        assertEquals(TestStrings.TEST_USER_EMAIL, userCreated.getEmail());
        assertEquals(Strings.USER_CREATED, userCreated.getMessage());
        assertNotNull(userCreated.getId());
    }

    @Test
    void saveShouldBeSuccessfullyAndSaveLowerCaseEmail() {
        UserPayload userPayload = UserUtils.getTestUserCreatePayloadUpperCase();
        User user = UserUtils.getTestUser();
        when(userDao.findByEmail(TestStrings.TEST_USER_EMAIL)).thenReturn(Optional.empty());
        when(modelMapper.map(userPayload, User.class)).thenReturn(user);
        when(userDao.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserCreated.class)).thenReturn(UserUtils.getTestUserCreated());
        UserCreated userCreated = userService.save(userPayload);
        assertEquals(TestStrings.TEST_USER_EMAIL, userCreated.getEmail());
        assertEquals(Strings.USER_CREATED, userCreated.getMessage());
        assertNotNull(userCreated.getId());
    }

    @Test
    void testGetUsernamePasswordTokenShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        UserPrincipalSecurity userPrincipalSecurity = UserUtils.getTestUserPrincipalSecurity();
        UserServiceImpl userService1 = Mockito.spy(userService);
        doReturn(user).when(userService1).findById(1L);
        when(modelMapper.map(user, UserPrincipalSecurity.class)).thenReturn(userPrincipalSecurity);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = userService1.getUsernamePasswordToken(1L);
        UserPrincipalSecurity userPrincipalSecurityRet = (UserPrincipalSecurity) usernamePasswordAuthenticationToken.getPrincipal();
        assertEquals(userPrincipalSecurity.getEmail(), userPrincipalSecurityRet.getEmail());
        assertEquals(userPrincipalSecurity.getId(), userPrincipalSecurityRet.getId());
    }

    @Test
    void testFindByIdShouldFailWhenUsernameNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testFindByIdShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        User userReturned = userService.findById(1L);
        assertEquals(user.getEmail(), userReturned.getEmail());
        assertEquals(user.getId(), userReturned.getId());
    }
}