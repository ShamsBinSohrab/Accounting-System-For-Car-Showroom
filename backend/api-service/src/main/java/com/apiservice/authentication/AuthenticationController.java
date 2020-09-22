package com.apiservice.authentication;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.model.auth.AuthRequest;
import com.apiservice.model.auth.AuthResponse;
import com.apiservice.model.auth.ForgetPasswordModel;
import com.apiservice.service.operator.OperatorService;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final ThreadPoolTaskExecutor taskExecutor;
  private final ApplicationContext applicationContext;
  private final OperatorService operatorService;
  private final PasswordResetMailSender passwordResetMailSender;

  @PostMapping(value = "/authenticate")
  public AuthResponse createAuthenticationToken(@RequestBody AuthRequest request)
      throws ExecutionException, InterruptedException {
    final UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authenticationToken);
    final TokenGenerator tokenGenerator = applicationContext.getBean(TokenGenerator.class);
    tokenGenerator.setUsername(request.getUsername());
    return taskExecutor.submitListenable(tokenGenerator).get();
  }

  @PostMapping(value = "/forgotPassword")
  public ResponseEntity<Void> forgotPassword(@RequestBody ForgetPasswordModel model) {
    final Operator operator = operatorService.getByEmail(model.getEmail());
    taskExecutor.execute(() -> passwordResetMailSender.send(operator.getEmail()));
    return ResponseEntity.ok().build();
  }
}

