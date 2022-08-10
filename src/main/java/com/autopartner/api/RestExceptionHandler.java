package com.autopartner.api;

import com.autopartner.api.dto.ErrorResponse;
import com.autopartner.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

  public static final int ERROR_CODE_FIELD_VALIDATION_FAILED = 402;

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
    String error = e.getBindingResult().getFieldErrors().toString();
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

  @ExceptionHandler(value = {NoSuchElementException.class})
  public ResponseEntity<ErrorResponse> handleException(NoSuchElementException e) {
    return error(NOT_FOUND, NOT_FOUND.value(), e.getMessage());
  }

  @ExceptionHandler(value = {UserAlreadyExistsException.class})
  public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e) {
    String error = e.getMessage();
    return error(BAD_REQUEST, ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

  private ResponseEntity<ErrorResponse> error(HttpStatus status, int code, String message) {
    final ErrorResponse response = new ErrorResponse(status.value(), code, message);
    log.info("Error response: {}", response);
    return ResponseEntity
        .status(status)
        .header("Content-Type", "application/json")
        .body(response);
  }

}