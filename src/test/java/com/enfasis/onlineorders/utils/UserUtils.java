package com.enfasis.onlineorders.utils;

import com.enfasis.onlineorders.dto.UserCreated;
import com.enfasis.onlineorders.dto.UserPrincipalSecurity;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.payload.UserPayload;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.security.UserAuthentication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserUtils {
    public static UserPayload getTestUserCreatePayload() {
        return UserPayload.builder().
                email(TestStrings.TEST_USER_EMAIL).
                password(TestStrings.TEST_USER_PASS).build();
    }
    public static UserPayload getTestUserCreatePayloadUpperCase() {
        return UserPayload.builder().
                email(TestStrings.TEST_USER_EMAIL.toUpperCase()).
                password(TestStrings.TEST_USER_PASS).build();
    }

    public static User getTestUser() {
        return User.builder().
                id(1L).
                email(TestStrings.TEST_USER_EMAIL).
                password(TestStrings.TEST_USER_PASS).build();
    }
    public static UserPrincipalSecurity getTestUserPrincipalSecurity(){
        return UserPrincipalSecurity.builder().email(TestStrings.TEST_USER_EMAIL).id(1L).build();
    }

    public static UserAuthentication getUserAuthentication() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        UserAuthentication userAuth = new UserAuthentication(TestStrings.TEST_USER_EMAIL,TestStrings.TEST_USER_PASS, authorities);
        userAuth.setId(1L);
        return userAuth;
    }
    public static UserCreated getTestUserCreated(){
        return UserCreated.builder().
                email(TestStrings.TEST_USER_EMAIL).
                id(1L).message(Strings.USER_CREATED).build();
    }
}
