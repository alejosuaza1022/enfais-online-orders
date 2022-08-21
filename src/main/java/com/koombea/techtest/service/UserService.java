package com.koombea.techtest.service;

import com.koombea.techtest.dto.UserCreated;
import com.koombea.techtest.model.User;
import com.koombea.techtest.payload.UserPayload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface UserService {
    UserCreated save(UserPayload user);

    UsernamePasswordAuthenticationToken getUsernamePasswordToken(Long userId);

    User findById(Long id);
}
