package com.apiservice.entity.master.password;

import com.apiservice.entity.master.operator.Operator;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "password_reset_confirmation_request", schema = "public")
public class PasswordResetConfirmationRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "token")
  private UUID token;

  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @Column(name = "expired_at")
  private ZonedDateTime expiredAt;

  @Column(name = "confirmed")
  private boolean confirmed;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "operator", nullable = false, updatable = false)
  private Operator operator;

  public static PasswordResetConfirmationRequest createRequest(Operator operator) {
    final PasswordResetConfirmationRequest passwordResetConfirmationRequest =
        new PasswordResetConfirmationRequest();
    passwordResetConfirmationRequest.operator = operator;
    passwordResetConfirmationRequest.createdAt = ZonedDateTime.now();
    passwordResetConfirmationRequest.expiredAt = ZonedDateTime.now().plusMinutes(5);
    passwordResetConfirmationRequest.token = UUID.randomUUID();
    return passwordResetConfirmationRequest;
  }
}
