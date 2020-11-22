package com.apiservice.authentication.password;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.password.PasswordResetConfirmationRequest;
import com.apiservice.repository.password.PasswordResetConfirmationRequestRepository;
import com.apiservice.utils.exceptions.PasswordResetConfirmationTokenException;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.management.relation.RoleUnresolved;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordResetTokenValidator {

  private final PasswordResetConfirmationRequestRepository confirmationTokenRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public PasswordResetConfirmationRequest validate(String token) {
    final UUID passwordResetToken = jwtTokenUtil.getSecretFromToken(token);
    return confirmationTokenRepository.findByTokenAndConfirmedIsFalse(passwordResetToken)
        .map(this::validateTokenExpiry)
        .orElseThrow(PasswordResetConfirmationTokenException::invalidToken);
  }

  private PasswordResetConfirmationRequest validateTokenExpiry(
      PasswordResetConfirmationRequest confirmationToken) {
    if (confirmationToken.getExpiredAt().compareTo(ZonedDateTime.now()) > 0) {
      confirmationToken.setConfirmed(true);
      confirmationTokenRepository.save(confirmationToken);
      return confirmationToken;
    }
    throw PasswordResetConfirmationTokenException.tokenExpired();
  }
}
