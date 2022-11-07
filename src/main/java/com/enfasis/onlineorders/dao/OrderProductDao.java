package com.enfasis.onlineorders.dao;

import com.enfasis.onlineorders.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductDao extends JpaRepository<OrderProduct, Long> {
}
