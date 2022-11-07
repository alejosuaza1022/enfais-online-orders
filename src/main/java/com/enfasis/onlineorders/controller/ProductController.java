package com.enfasis.onlineorders.controller;

import com.enfasis.onlineorders.constants.Permissions;
import com.enfasis.onlineorders.constants.Routes;
import com.enfasis.onlineorders.dto.product.ProductDto;
import com.enfasis.onlineorders.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(Routes.API_ROUTE + Routes.PRODUCTS_ROUTE)
public class ProductController {
    private ProductService productService;

    @PostMapping
    @PreAuthorize(Permissions.AUTHORITY_PRODUCT_CREATE)
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @GetMapping(Routes.ID)
    public ResponseEntity<ProductDto> getProductById(@PathVariable  Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }
}
