package com.autopartner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class NotFoundException extends RuntimeException {
    String className;
    Long id;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return className + " with id=" + id + " is not found";
    }
}
