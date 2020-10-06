package com.apiservice.utils.exceptions;

public class PasswordResetConfirmationTokenException extends RuntimeException {

  private PasswordResetConfirmationTokenException(String message) {
    super(message);
  }

  public static PasswordResetConfirmationTokenException invalidToken() {
    return new PasswordResetConfirmationTokenException("Token is not valid");
  }

  public static PasswordResetConfirmationTokenException tokenExpired() {
    return new PasswordResetConfirmationTokenException("Token has expired");
  }

}
