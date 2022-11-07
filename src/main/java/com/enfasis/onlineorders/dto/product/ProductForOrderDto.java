package com.enfasis.onlineorders.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductForOrderDto {
    @NotNull
    private Long id;
    @NotNull
    private Integer quantity;
}
