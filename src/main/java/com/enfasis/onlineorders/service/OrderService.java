package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.dto.order.OrderCreateDto;
import com.enfasis.onlineorders.dto.order.OrderDto;
import com.enfasis.onlineorders.dto.order.OrderProjection;
import com.enfasis.onlineorders.model.User;

import java.util.List;

public interface OrderService {
    OrderDto createOrderProduct(OrderCreateDto orderCreateDto, User user);
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    List<OrderProjection> getOrdersByUserId(Long userId);
}
