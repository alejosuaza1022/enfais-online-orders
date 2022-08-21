package com.koombea.techtest.service.impl;

import com.koombea.techtest.constants.StringExceptions;
import com.koombea.techtest.constants.Strings;
import com.koombea.techtest.dao.UserDao;
import com.koombea.techtest.model.User;
import com.koombea.techtest.security.UserAuthentication;
import com.koombea.techtest.security.utils.JwtManage;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthenticationService implements UserDetailsService {
    private final UserDao userDao;
    private JwtManage jwtManage;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userDao.findByEmail(username);
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException(StringExceptions.USER_NOT_FOUND));
        UserAuthentication userAuth = new UserAuthentication(user.getEmail(), user.getPassword(),new ArrayList<>());
        userAuth.setId(user.getId());
        return userAuth;
    }
    public Map<String, Object> logInUser(Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        final String token = jwtManage.generateToken(userAuthentication.getId(),
                userAuthentication.getUsername());
        return Map.of(Strings.ACCESS_TOKEN,token);
    }
}
