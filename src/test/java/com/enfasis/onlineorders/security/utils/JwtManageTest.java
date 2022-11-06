package com.enfasis.onlineorders.security.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.enfasis.onlineorders.exeption.custom.BadDataException;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.utils.TestStrings;
import com.enfasis.onlineorders.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtManageTest {
    private JwtManage jwtManage;
    @BeforeEach
    void setUp() {
        jwtManage = new JwtManage(TestStrings.DUMMY_JWT_SECRET);
    }

    @Test
    void testGenerateTokenShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        String token = jwtManage.generateToken(user.getId(),
                user.getEmail());
        assertNotNull(token);
        assertNotEquals("", token);
    }

    @Test
    void testGenerateTokenShouldFailWhenDataIncorrect() {
        assertThrows(BadDataException.class, () ->
                jwtManage.generateToken(null, "a@a.com"));
        assertThrows(BadDataException.class, () ->
                jwtManage.generateToken(1L, null));
        assertThrows(BadDataException.class, () ->
                jwtManage.generateToken(1L, ""));
    }

    @Test
    void testVerifyTokenShouldSuccessWhenDataCorrect() {
        User user = UserUtils.getTestUser();
        String token = jwtManage.generateToken(user.getId(), user.getEmail());
        Long idUser = jwtManage.verifyToken(Strings.BEARER + token);
        assertEquals(idUser, user.getId());
    }

    @Test
    void testVerifyTokenShouldFailWhenDataIncorrect() throws BadDataException {
        User user = UserUtils.getTestUser();
        String token = jwtManage.generateToken(user.getId(), user.getEmail() );
        assertThrows(JWTVerificationException.class, () ->
                jwtManage.verifyToken(Strings.BEARER + token + "asd" )
        );
        assertThrows(JWTVerificationException.class, () ->
                jwtManage.verifyToken(Strings.BEARER )
        );
        assertThrows(BadDataException.class, () ->
                jwtManage.verifyToken("" )
        );
        assertThrows(BadDataException.class, () ->
                jwtManage.verifyToken(null)
        );
    }
}