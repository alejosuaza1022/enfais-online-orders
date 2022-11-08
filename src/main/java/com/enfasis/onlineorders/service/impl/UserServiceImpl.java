package com.enfasis.onlineorders.service.impl;

import com.enfasis.onlineorders.constants.StringExceptions;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.dao.RoleDao;
import com.enfasis.onlineorders.dao.UserDao;
import com.enfasis.onlineorders.dto.order.OrderProjection;
import com.enfasis.onlineorders.dto.user.UserCreated;
import com.enfasis.onlineorders.dto.user.UserDto;
import com.enfasis.onlineorders.dto.user.UserPrincipalSecurity;
import com.enfasis.onlineorders.exeption.custom.UniqueConstraintViolationException;
import com.enfasis.onlineorders.model.Permission;
import com.enfasis.onlineorders.model.Role;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.payload.UserPayload;
import com.enfasis.onlineorders.payload.UserUpdatePayload;
import com.enfasis.onlineorders.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;
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
        Role role = roleDao.findRoleByName(Strings.ROLE_USER);
        userToSave.setRole(role);
        UserCreated userCreated = modelMapper.map(userDao.save(userToSave), UserCreated.class);
        userCreated.setMessage(Strings.USER_CREATED);
        return userCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public UsernamePasswordAuthenticationToken getUsernamePasswordToken(Long userId) {
        User user = this.findById(userId);
        Role role = user.getRole();
        Set<Permission> permissions = role.getPermissions();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        UserPrincipalSecurity userPrincipalSecurity = modelMapper.map(user, UserPrincipalSecurity.class);
        return new UsernamePasswordAuthenticationToken(userPrincipalSecurity, null, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).
                orElseThrow(() -> new UsernameNotFoundException(StringExceptions.USER_NOT_FOUND));
    }

    @Override
    @Cacheable(value = Strings.CACHE_USER, key = "#id")
    @Transactional(readOnly = true)
    public UserDto findByIdDto(Long id) {
        return modelMapper.map(this.findById(id), UserDto.class);
    }

    @Override
    @CachePut(value = Strings.CACHE_USER, key = "#id")
    @Transactional
    public UserDto updateUserById(Long id, UserUpdatePayload payload) {
        User user = this.findById(id);
        if (payload.getEmail() != null)
            user.setEmail(payload.getEmail());
        return modelMapper.map(userDao.save(user), UserDto.class);

    }


    @Override
    @Transactional(readOnly = true)
    public User getUserFromContext() {
        return this.findById(getUserPrincipalFromContext().getId());
    }

    @Override
    public UserPrincipalSecurity getUserPrincipalFromContext() {
        return (UserPrincipalSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
