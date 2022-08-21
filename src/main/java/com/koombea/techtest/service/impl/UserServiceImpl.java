package com.koombea.techtest.service.impl;

import com.koombea.techtest.constants.StringExceptions;
import com.koombea.techtest.constants.Strings;
import com.koombea.techtest.dao.UserDao;
import com.koombea.techtest.dto.UserCreated;
import com.koombea.techtest.dto.UserPrincipalSecurity;
import com.koombea.techtest.exeption.custom.UniqueConstraintViolationException;
import com.koombea.techtest.model.User;
import com.koombea.techtest.payload.UserPayload;
import com.koombea.techtest.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCreated save(UserPayload user) {
        user.setEmail(user.getEmail().toLowerCase());
        userDao.findByEmail(user.getEmail()).ifPresent(x -> {
            throw new UniqueConstraintViolationException(StringExceptions.USER_ALREADY_EXIST);
        });
        User userToSave = modelMapper.map(user, User.class);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        UserCreated userCreated = modelMapper.map(userDao.save(userToSave), UserCreated.class);
        userCreated.setMessage(Strings.USER_CREATED);
        return userCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public UsernamePasswordAuthenticationToken getUsernamePasswordToken(Long userId) {
        User user = this.findById(userId);
        UserPrincipalSecurity userPrincipalSecurity = modelMapper.map(user, UserPrincipalSecurity.class);
        return new UsernamePasswordAuthenticationToken(userPrincipalSecurity, null, new ArrayList<>());
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).
                orElseThrow(() -> new UsernameNotFoundException(StringExceptions.USER_NOT_FOUND));
    }
}
