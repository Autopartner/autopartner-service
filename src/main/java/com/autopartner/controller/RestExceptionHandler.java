package com.autopartner.controller;

import com.autopartner.controller.dto.ErrorDto;
import com.autopartner.exception.UserAlreadyExistsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RestExceptionHandler {

  public static final int ERROR_CODE_FIELD_VALIDATION_FAILED = 402;

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ErrorDto handleException(MethodArgumentNotValidException e) {
    String error = e.getBindingResult().getFieldErrors().toString();
    return new ErrorDto(BAD_REQUEST.value(), ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

  @ExceptionHandler(value = {UserAlreadyExistsException.class})
  public ErrorDto handleException(UserAlreadyExistsException e) {
    String error = e.getMessage();
    return new ErrorDto(BAD_REQUEST.value(), ERROR_CODE_FIELD_VALIDATION_FAILED, error);
  }

}
