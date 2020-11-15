package com.apiservice.authentication;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuthConstants {

  public final String tenantIdentifier = "x-company-accessor-token";
  public final List<String> ALLOWED_PATHS =
      List.of("/authenticate", "/forgotPassword", "/confirmResetPassword");
  public final List<String> ALLOWED_METHODS = List.of("OPTIONS");

}
