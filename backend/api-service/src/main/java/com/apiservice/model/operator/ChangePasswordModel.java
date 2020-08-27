package com.apiservice.model.operator;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordModel {

  @NotEmpty(message = "old password is required")
  private String oldPassword;

  @NotEmpty(message = "new password is required")
  private String newPassword;

  @NotEmpty(message = "password confirmation is required")
  private String confirmPassword;
}
