package com.apiservice.authentication.password;

import com.apiservice.entity.master.password.PasswordResetConfirmationToken;
import com.apiservice.repository.password.PasswordResetConfirmationTokenRepository;
import com.apiservice.utils.exceptions.PasswordResetConfirmationTokenException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenValidator {

  private final PasswordResetConfirmationTokenRepository confirmationTokenRepository;

  public PasswordResetConfirmationToken validate(UUID token) {
    return confirmationTokenRepository.findByTokenAndConfirmedIsFalse(token)
        .map(this::validateTokenExpiry)
        .orElseThrow(PasswordResetConfirmationTokenException::invalidToken);
  }

  private PasswordResetConfirmationToken validateTokenExpiry(
      PasswordResetConfirmationToken confirmationToken) {
    if (confirmationToken.getExpiredAt().compareTo(LocalDateTime.now()) > 0) {
      confirmationToken.setConfirmed(true);
      confirmationTokenRepository.save(confirmationToken);
      return confirmationToken;
    }
    throw PasswordResetConfirmationTokenException.tokenExpired();
  }
}
