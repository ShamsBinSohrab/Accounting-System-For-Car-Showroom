package com.apiservice.authentication.controller;

import com.apiservice.authentication.TokenGenerator;
import com.apiservice.model.jwt.AuthRequest;
import com.apiservice.model.jwt.AuthResponse;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
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
}

