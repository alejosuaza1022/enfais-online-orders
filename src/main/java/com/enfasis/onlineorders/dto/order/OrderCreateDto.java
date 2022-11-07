package com.enfasis.onlineorders.dto.order;

import com.enfasis.onlineorders.dto.product.ProductForOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderCreateDto {
    private String description;
    private List<ProductForOrderDto> products;
}
