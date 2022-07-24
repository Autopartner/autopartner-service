package com.autopartner.controller.dto;

import static org.springframework.util.Assert.notNull;

public record ErrorDto(Integer status, Integer code, String message) {

  public ErrorDto(Integer status, Integer code, String message) {
    this.status = status;
    notNull(code, "Code cannot be null.");
    notNull(message, "Message cannot be null.");
    this.code = code;
    this.message = message;
  }
}

