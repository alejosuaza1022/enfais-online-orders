package com.enfasis.onlineorders.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto implements Serializable {
    @NotEmpty
    private String name;
    @NotNull
    private Double price;
    private Long id;
    private String description;
    private Integer quantity;
}
