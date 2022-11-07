package com.enfasis.onlineorders.dto.orderproduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderProductDto implements Serializable {
    private Integer quantity;
    private String productName;
    private Double productPrice;
    private String productId;
}
