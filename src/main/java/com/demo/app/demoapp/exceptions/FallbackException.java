package com.demo.app.demoapp.exceptions;

public class FallbackException extends RuntimeException{
    public FallbackException(String message) {
        super(message);
    }
}
