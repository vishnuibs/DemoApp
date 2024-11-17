package com.demo.app.demoapp.exceptions;

public class ClientErrorException extends RuntimeException {
    public ClientErrorException(String s) {
        super(s);
    }
}
