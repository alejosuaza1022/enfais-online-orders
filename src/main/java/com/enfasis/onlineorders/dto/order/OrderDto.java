package com.enfasis.onlineorders.dto.order;

import com.enfasis.onlineorders.dto.orderproduct.OrderProductDto;
import com.enfasis.onlineorders.model.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDto implements Serializable {
    private Long id;
    private Double totalPaid;
    private String description;
    private Instant createdAt;
    private List<OrderProductDto> orderProducts;
    private String userEmail;
    private String userId;
}
