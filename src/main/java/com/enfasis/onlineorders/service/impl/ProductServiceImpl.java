package com.enfasis.onlineorders.service.impl;

import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.dao.ProductDao;
import com.enfasis.onlineorders.dto.product.ProductDto;
import com.enfasis.onlineorders.exeption.custom.ResourceNotFoundException;
import com.enfasis.onlineorders.model.Product;
import com.enfasis.onlineorders.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product = productDao.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = Strings.CACHE_PRODUCT, key = "#id")
    public ProductDto getProductById(Long id) {
        return productDao.findById(id)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(Strings.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findAllByIds(Iterable<Long> ids) {
        return productDao.findAllById(ids);
    }
}
