package com.enfasis.onlineorders.service.impl;

import com.enfasis.onlineorders.constants.StringExceptions;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.dao.OrderDao;
import com.enfasis.onlineorders.dto.order.OrderCreateDto;
import com.enfasis.onlineorders.dto.order.OrderDto;
import com.enfasis.onlineorders.dto.order.OrderProjection;
import com.enfasis.onlineorders.dto.product.ProductForOrderDto;
import com.enfasis.onlineorders.exeption.custom.ResourceNotFoundException;
import com.enfasis.onlineorders.model.Order;
import com.enfasis.onlineorders.model.OrderProduct;
import com.enfasis.onlineorders.model.Product;
import com.enfasis.onlineorders.model.User;
import com.enfasis.onlineorders.service.OrderService;
import com.enfasis.onlineorders.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private ProductService productService;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    @CacheEvict(value = Strings.CACHE_ALL_ORDERS, allEntries = true)
    public OrderDto createOrderProduct(OrderCreateDto orderProductDto, User user) {
        Order order = Order.builder().description(orderProductDto.getDescription()).user(user).build();
        order = orderDao.saveAndFlush(order);
        createOrderProducts(order, orderProductDto.getProducts());
        return modelMapper.map(orderDao.save(order), OrderDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = Strings.CACHE_ORDER, key = "#id")
    public OrderDto getOrderById(Long id) {
        return modelMapper.map(orderDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(StringExceptions.ORDER_NOT_FOUND)), OrderDto.class);

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = Strings.CACHE_ALL_ORDERS)
    public List<OrderDto> getAllOrders() {
        return orderDao.findAll().stream().
                map(order -> modelMapper.map(order, OrderDto.class)).toList();
    }

    @Override
    public List<OrderProjection> getOrdersByUserId(Long userId) {
        return orderDao.findAllByUserId(userId);
    }


    private OrderProduct createOrderProduct(Order order, Product product, Integer quantity) {
        return OrderProduct.builder().order(order).product(product).quantity(quantity).build();
    }

    private void createOrderProducts(Order order, List<ProductForOrderDto> productForOrderDtos) {
        double totalPrice = 0;
        List<OrderProduct> orderProductList = new ArrayList<>();
        Map<Long, Integer> productQuantityMap = productForOrderDtos.stream().collect(
                Collectors.toMap(ProductForOrderDto::getId, ProductForOrderDto::getQuantity));
        List<Product> products = productService.findAllByIds(productQuantityMap.keySet());
        for (Product product : products) {
            totalPrice += product.getPrice() * productQuantityMap.get(product.getId());
            orderProductList.add(createOrderProduct(order, product, productQuantityMap.get(product.getId())));
        }
        order.setOrderProducts(orderProductList);
        order.setTotalPaid(totalPrice);
    }
}
