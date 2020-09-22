package com.apiservice.model.auth;

import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgetPasswordModel {

  @Email(message = "Invalid email address")
  private String email;
}
