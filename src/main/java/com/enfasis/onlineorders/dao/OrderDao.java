package com.enfasis.onlineorders.dao;

import com.enfasis.onlineorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {
}
