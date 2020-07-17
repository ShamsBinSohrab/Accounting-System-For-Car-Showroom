package com.apiservice.utils.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(
      RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(ex, "Invalid request",
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleException(
      RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
