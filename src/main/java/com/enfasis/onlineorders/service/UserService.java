package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.dto.UserCreated;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.payload.UserPayload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface UserService {
    UserCreated save(UserPayload user);

    UsernamePasswordAuthenticationToken getUsernamePasswordToken(Long userId);

    User findById(Long id);
}
