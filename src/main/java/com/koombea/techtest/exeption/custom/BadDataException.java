package com.koombea.techtest.exeption.custom;

public class BadDataException extends RuntimeException {
    public BadDataException(String msg) {
        super(msg);
    }
}

