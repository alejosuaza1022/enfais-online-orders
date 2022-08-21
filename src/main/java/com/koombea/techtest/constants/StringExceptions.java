package com.koombea.techtest.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringExceptions {
    public static final String USER_ALREADY_EXIST = "there is already a user registered with that email";
    public static final String INVALID_PASSWORD = "Password must have, 1 Capital, 1 character, 1 Special character, 1 Number and 8 to 20 digits.";
    public static final String USER_NOT_FOUND = "The user was not found on the database.";
    public static final String JWT_ERROR_TOKEN_VERIFICATION = "The token is not valid for this user.";
}
