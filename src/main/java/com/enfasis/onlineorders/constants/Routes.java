package com.enfasis.onlineorders.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Routes {
    public static final String API_ROUTE = "/api/v1";
    public static final String USERS_ROUTE = "/users";
    public static final String LOGIN_ROUTE = "/login";
    public static final String PRODUCTS_ROUTE = "/products";
    public static final String ORDERS_ROUTE = "/orders";
    public static final String ID = "/{id}";
}
