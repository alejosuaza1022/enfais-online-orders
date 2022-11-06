package com.enfasis.onlineorders.controller;

import com.enfasis.onlineorders.constants.Permissions;
import com.enfasis.onlineorders.constants.Routes;
import com.enfasis.onlineorders.dto.UserCreated;
import com.enfasis.onlineorders.dto.UserDto;
import com.enfasis.onlineorders.dto.UserPrincipalSecurity;
import com.enfasis.onlineorders.payload.UserPayload;
import com.enfasis.onlineorders.payload.UserUpdatePayload;
import com.enfasis.onlineorders.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<UserCreated> create(@Valid @RequestBody UserPayload user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByIdDto(id));
    }

    @PutMapping()
    public ResponseEntity<UserDto> getUser(@RequestBody UserUpdatePayload payload) {
        UserPrincipalSecurity userPrincipalSecurity = (UserPrincipalSecurity)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userPrincipalSecurity.getId(), payload));
    }

    @GetMapping
    @PreAuthorize(Permissions.AUTHORITY_ORDERS_VIEW_ALL)
    public String example() {
        return "Just view all orders permission";
    }
}
