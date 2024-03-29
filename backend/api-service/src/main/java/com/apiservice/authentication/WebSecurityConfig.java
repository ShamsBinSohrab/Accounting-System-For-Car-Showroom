package com.apiservice.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationEntryPointImpl authenticationEntryPoint;
  private final UserDetailsService userDetailsService;
  private final AuthenticationRequestFilter authenticationRequestFilter;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/v1/cars/**").hasAuthority(Scopes.CAR_READ.getAuthority())
        .antMatchers(HttpMethod.GET, "/v1/purchaseRecords/**").hasAuthority(Scopes.PURCHASE_RECORD_READ.getAuthority())
        .antMatchers(HttpMethod.GET, "/v1/sellRecords/**").hasAuthority(Scopes.SELL_RECORD_READ.getAuthority())
        .antMatchers(HttpMethod.GET, "/v1/operators/**").hasAuthority(Scopes.OPERATOR_READ.getAuthority())
        .antMatchers(HttpMethod.GET, "/v1/companies/**").hasAuthority(Scopes.COMPANY_READ.getAuthority())
        .antMatchers(HttpMethod.GET, "/v1/expenseRecords/**").hasAuthority(Scopes.EXPENSE_RECORD_READ.getAuthority())
        .antMatchers("/v1/cars/**").hasAuthority(Scopes.CAR_WRITE.getAuthority())
        .antMatchers("/v1/purchaseRecords/**").hasAuthority(Scopes.PURCHASE_RECORD_WRITE.getAuthority())
        .antMatchers("/v1/sellRecords/**").hasAuthority(Scopes.SELL_RECORD_WRITE.getAuthority())
        .antMatchers("/v1/operators/**").hasAuthority(Scopes.OPERATOR_WRITE.getAuthority())
        .antMatchers("/v1/companies/**").hasAuthority(Scopes.COMPANY_WRITE.getAuthority())
        .antMatchers("/v1/expenseRecords/**").hasAuthority(Scopes.EXPENSE_RECORD_WRITE.getAuthority())
        .antMatchers("/authenticate", "/forgotPassword", "/confirmResetPassword").permitAll()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity
        .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}

