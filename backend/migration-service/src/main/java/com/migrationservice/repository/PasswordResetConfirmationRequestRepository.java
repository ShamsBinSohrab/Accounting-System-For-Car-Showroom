package com.migrationservice.repository;

import com.migrationservice.entity.PasswordResetConfirmationRequest;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetConfirmationRequestRepository
    extends JpaRepository<PasswordResetConfirmationRequest, Long> {

  List<PasswordResetConfirmationRequest> findAllByExpiredAtBefore(ZonedDateTime dateTime);
}
