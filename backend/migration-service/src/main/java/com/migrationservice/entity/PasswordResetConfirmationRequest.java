package com.migrationservice.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "password_reset_confirmation_request", schema = "public")
public class PasswordResetConfirmationRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "expired_at")
  private ZonedDateTime expiredAt;

  @Column(name = "confirmed")
  private boolean confirmed;
}
