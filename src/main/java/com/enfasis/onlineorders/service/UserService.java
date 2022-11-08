package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.dto.order.OrderProjection;
import com.enfasis.onlineorders.dto.user.UserCreated;
import com.enfasis.onlineorders.dto.user.UserDto;
import com.enfasis.onlineorders.dto.user.UserPrincipalSecurity;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.payload.UserPayload;
import com.enfasis.onlineorders.payload.UserUpdatePayload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public interface UserService {
    UserCreated save(UserPayload user);

    UsernamePasswordAuthenticationToken getUsernamePasswordToken(Long userId);

    User findById(Long id);

    UserDto findByIdDto(Long id);

    UserDto updateUserById(Long id, UserUpdatePayload payload);

    User getUserFromContext();

    UserPrincipalSecurity getUserPrincipalFromContext();

}
