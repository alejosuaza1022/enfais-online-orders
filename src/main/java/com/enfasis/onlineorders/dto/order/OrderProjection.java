package com.enfasis.onlineorders.dto.order;

import com.enfasis.onlineorders.dto.orderproduct.OrderProductDto;
import com.enfasis.onlineorders.dto.orderproduct.OrderProductProjection;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
public class OrderProjection {
    private Long id;
    private Double totalPaid;
    private String description;
    private Instant createdAt;
    private List<OrderProductProjection> orderProducts;
    private String userEmail;
    private String userId;
}
