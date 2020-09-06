package com.apiservice.model.operator;

import com.apiservice.entity.master.operator.Operator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordChangeValidator {

  private final PasswordEncoder passwordEncoder;

  public void validatePasswordChange(ChangePasswordModel model, Operator operator) {
    if (model.getNewPassword().equals(model.getConfirmPassword())) {
      if (!passwordEncoder.matches(model.getNewPassword(), operator.getPassword())) {
        if (passwordEncoder.matches(model.getOldPassword(), operator.getPassword())) {
          return;
        }
        throw new PasswordChangeValidationException("old password is incorrect");
      }
      throw new PasswordChangeValidationException("new password cannot be same as old password");
    }
    throw new PasswordChangeValidationException("new password and confirm password must be same");
  }

  public void validatePasswordReset(ResetPasswordModel model) {
    if (!model.getNewPassword().equals(model.getConfirmPassword())) {
      throw new PasswordChangeValidationException("new password and confirm password must be same");
    }
  }
}
