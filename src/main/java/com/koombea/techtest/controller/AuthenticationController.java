package com.koombea.techtest.controller;

import com.koombea.techtest.constants.Routes;
import com.koombea.techtest.payload.UserPayload;
import com.koombea.techtest.service.impl.UserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(Routes.API_ROUTE)
@AllArgsConstructor
public class AuthenticationController {
    private UserAuthenticationService userAuthenticationService;
    private AuthenticationManager authenticationManager;

    @PostMapping(value = Routes.LOGIN_ROUTE)
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@Valid @RequestBody UserPayload authenticationDto) {
        Authentication authentication = authenticate(authenticationDto.getEmail().toLowerCase(), authenticationDto.getPassword());
        return ResponseEntity.ok(userAuthenticationService.logInUser(authentication));
    }

    private Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
