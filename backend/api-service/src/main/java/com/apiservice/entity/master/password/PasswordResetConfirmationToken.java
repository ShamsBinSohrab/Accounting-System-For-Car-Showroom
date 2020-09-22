package com.apiservice.entity.master.password;

import com.apiservice.entity.master.operator.Operator;
import java.time.LocalDateTime;
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
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "password_reset_confirmation_token", schema = "public")
public class PasswordResetConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "token")
  private UUID token;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "operator", nullable = false, updatable = false)
  private Operator operator;

  public static PasswordResetConfirmationToken generateNewToken(Operator operator) {
    final PasswordResetConfirmationToken passwordResetConfirmationToken =
        new PasswordResetConfirmationToken();
    passwordResetConfirmationToken.operator = operator;
    passwordResetConfirmationToken.createdAt = LocalDateTime.now();
    passwordResetConfirmationToken.expiredAt = LocalDateTime.now().plusMinutes(5);
    passwordResetConfirmationToken.token = UUID.randomUUID();
    return passwordResetConfirmationToken;
  }
}
