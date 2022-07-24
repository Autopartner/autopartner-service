package com.autopartner.controller.DTO;

import static org.springframework.util.Assert.notNull;

final class ErrorDTO {

  private final int status;
  private final int code;
  private final String message;

  public ErrorDTO(int status, int code, String message) {
    this.status = status;
    notNull(code, "Code cannot be null.");
    notNull(message, "Message cannot be null.");
    this.code = code;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}

