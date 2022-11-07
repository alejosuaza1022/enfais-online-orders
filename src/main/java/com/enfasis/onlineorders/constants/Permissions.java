package com.enfasis.onlineorders.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access =  AccessLevel.PRIVATE)
public final class Permissions {
    public static final String AUTHORITY_ORDERS_VIEW_ALL = "hasAuthority('ORDERS:VIEW_ALL')";
    public static final String AUTHORITY_PRODUCT_CREATE = "hasAuthority('PRODUCT:CREATE')";
}
