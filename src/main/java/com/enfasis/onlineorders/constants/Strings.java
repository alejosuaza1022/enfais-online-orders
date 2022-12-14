package com.enfasis.onlineorders.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MESSAGE_RESPONSE = "message";
    public static final String DATA = "data";
    public static final String USER_CREATED = "User successfully created";
    public static final String USER_ID_BODY = "userId";
    public static final String BAD_DATA_FOR_TOKEN_GENERATION = "The data required for the token generation is not provided.";
    public static final String BAD_DATA_FOR_TOKEN_VERIFICATION = "The data required for the verification is missing.";
    public static final String BEARER = "Bearer ";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String AUTHORIZATION_BODY = "Authorization";
    public static final String ROLE_USER = "USER";
    public static final String CACHE_USER = "users";
    public static final String CACHE_PRODUCT = "products";
    public static final String CACHE_API_HIT_COUNT = "apiHitCount";
    public static final String CACHE_ORDER = "orders";
    public static final String CACHE_ALL_ORDERS = "all_orders";
    public static final String PRODUCT_NOT_FOUND = "The product was not found on our DB.";
}
