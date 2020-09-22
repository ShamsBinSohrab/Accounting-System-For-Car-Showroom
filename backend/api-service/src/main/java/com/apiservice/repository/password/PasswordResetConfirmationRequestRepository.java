package com.apiservice.repository.password;

import com.apiservice.entity.master.password.PasswordResetConfirmationRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetConfirmationRequestRepository
    extends JpaRepository<PasswordResetConfirmationRequest, Long> {

  Optional<PasswordResetConfirmationRequest> findByTokenAndConfirmedIsFalse(UUID token);
}
