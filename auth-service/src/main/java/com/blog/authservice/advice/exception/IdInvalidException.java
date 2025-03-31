package com.blog.authservice.advice.exception;

public class IdInvalidException extends Exception {
    // Constructor that accepts a message
    public IdInvalidException(String message) {
        super(message);
    }
}
