package com.autopartner.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
