package com.enfasis.onlineorders.service;

import com.enfasis.onlineorders.dto.product.ProductDto;
import com.enfasis.onlineorders.model.Product;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<Product> findAllByIds(Iterable<Long> ids);
}
