package com.autopartner.controller.DTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestExceptionHandler {

  public static final int ERROR_CODE_FIELD_VALIDATION_FAILED = 402;

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorDTO> handleException(MethodArgumentNotValidException e) {
    String error = e.getBindingResult().getFieldErrors().toString();
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

  private ResponseEntity<ErrorDTO> error(HttpStatus status, int code, String message) {
    ErrorDTO response = new ErrorDTO(status.value(), code, message);
    return ResponseEntity.status(status).body(response);
  }
}
