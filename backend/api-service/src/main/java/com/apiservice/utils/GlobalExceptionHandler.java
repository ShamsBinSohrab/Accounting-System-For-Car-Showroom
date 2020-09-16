package com.apiservice.utils;

import com.apiservice.authentication.AuthenticationException;
import com.apiservice.model.company.CompanyCreationException;
import com.apiservice.model.operator.PasswordChangeValidationException;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFoundException(
      RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(
      RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, "Invalid request", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(PasswordChangeValidationException.class)
  protected ResponseEntity<Object> handlePasswordChangeValidationException(
      PasswordChangeValidationException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<Object> handleAuthenticationException(
      AuthenticationException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  @ExceptionHandler(CompanyCreationException.class)
  protected ResponseEntity<Object> handleCompanyCreationException(
      CompanyCreationException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    final String message = ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
    return handleExceptionInternal(
        ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
      WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
