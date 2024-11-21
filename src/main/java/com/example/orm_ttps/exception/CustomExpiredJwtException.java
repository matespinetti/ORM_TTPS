package com.example.orm_ttps.exception;

public class CustomExpiredJwtException extends RuntimeException{
    public CustomExpiredJwtException(String message) {
        super(message);
    }
}
