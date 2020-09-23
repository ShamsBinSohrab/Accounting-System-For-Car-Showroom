package com.migrationservice.cleanup;

import com.migrationservice.entity.PasswordResetConfirmationRequest;
import com.migrationservice.repository.PasswordResetConfirmationRequestRepository;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordResetConfirmationRequestCleanup {

  private final PasswordResetConfirmationRequestRepository confirmationRequestRepository;

  @Transactional
  @Scheduled(cron = "0 27 2 1/1 * ?")
  public void cleanup() {
    final ZonedDateTime sevenDaysAgo =
        ZonedDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    final List<PasswordResetConfirmationRequest> list =
        confirmationRequestRepository.findAllByExpiredAtBefore(sevenDaysAgo);

    log.info("Deleting {} password reset confirmation requests", list.size());

    confirmationRequestRepository.deleteAll(list);
  }
}
