package com.apiservice.repository.password;

import com.apiservice.entity.master.password.PasswordResetConfirmationToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetConfirmationTokenRepository
    extends JpaRepository<PasswordResetConfirmationToken, Long> {

  Optional<PasswordResetConfirmationToken> findByToken(UUID token);
}
