package com.apiservice.authentication;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationException extends RuntimeException {

  public AuthenticationException(Throwable cause) {
    super(cause);
  }
}
