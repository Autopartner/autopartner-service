package com.autopartner.exception;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String entityName, String param ) {
        super(entityName + " with param: " + param + " already exists");
    }
}
