package com.enfasis.onlineorders.dao;

import com.enfasis.onlineorders.dto.order.OrderProjection;
import com.enfasis.onlineorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {
    List<OrderProjection> findAllByUserId(Long userId);
}
