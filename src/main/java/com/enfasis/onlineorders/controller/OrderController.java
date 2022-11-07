package com.enfasis.onlineorders.controller;

import com.enfasis.onlineorders.constants.Permissions;
import com.enfasis.onlineorders.constants.Routes;
import com.enfasis.onlineorders.dto.order.OrderCreateDto;
import com.enfasis.onlineorders.dto.order.OrderDto;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.service.OrderService;
import com.enfasis.onlineorders.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Routes.API_ROUTE + Routes.ORDERS_ROUTE)
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        User user = userService.getUserFromContext();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrderProduct(orderCreateDto, user));
    }
    @GetMapping(Routes.ID)
    @PreAuthorize(Permissions.AUTHORITY_ORDERS_VIEW_ALL)
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }
    @GetMapping
    @PreAuthorize(Permissions.AUTHORITY_ORDERS_VIEW_ALL)
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

}
