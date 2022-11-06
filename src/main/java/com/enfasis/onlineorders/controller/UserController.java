package com.enfasis.onlineorders.controller;

import com.enfasis.onlineorders.dto.UserCreated;
import com.enfasis.onlineorders.payload.UserPayload;
import com.enfasis.onlineorders.service.UserService;
import com.enfasis.onlineorders.constants.Routes;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Routes.API_ROUTE + Routes.USERS_ROUTE)
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserCreated> create(@Valid @RequestBody UserPayload user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }
}
