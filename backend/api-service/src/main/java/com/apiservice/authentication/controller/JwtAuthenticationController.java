package com.apiservice.authentication.controller;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.authentication.service.JwtUserDetailsService;
import com.apiservice.entity.company.Company;
import com.apiservice.entity.operator.Operator;
import com.apiservice.model.jwt.JwtRequest;
import com.apiservice.model.jwt.JwtResponse;
import com.apiservice.model.operator.OperatorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService userDetailsService;

  @PostMapping(value = "/authenticate")
  public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
    final UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authenticationToken);
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    final Company company = userDetailsService.loadCompanyUuidByUsername(request.getUsername());
    return JwtResponse.prepare(
        userDetails, company, jwtTokenUtil::generateAuthToken, jwtTokenUtil::generateTenantToken);
  }
}

