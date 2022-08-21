package com.koombea.techtest.controller;

import com.koombea.techtest.constants.Routes;
import com.koombea.techtest.dto.UserCreated;
import com.koombea.techtest.payload.UserPayload;
import com.koombea.techtest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
