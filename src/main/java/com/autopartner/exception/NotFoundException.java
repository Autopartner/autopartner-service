package com.autopartner.exception;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class NotFoundException extends RuntimeException {

  public NotFoundException(String entity, Long id) {
    super(entity + " with id=" + id + " is not found");
  }
}
