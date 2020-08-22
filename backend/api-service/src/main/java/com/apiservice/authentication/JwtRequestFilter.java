package com.apiservice.authentication;

import com.apiservice.authentication.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUserDetailsService jwtUserDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    Optional.ofNullable(request.getHeader("Authorization"))
        .ifPresentOrElse(
            header -> {
              try {
                final String jwtToken = header.substring(7);
                final String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                  final UserDetails details = jwtUserDetailsService.loadUserByUsername(username);
                  if (jwtTokenUtil.validateToken(jwtToken, details)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            details, null, details.getAuthorities());
                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  }
                } else {
                  log.warn("Authentication already set");
                }
              } catch (IllegalArgumentException ex) {
                log.error("Unable to get JWT token");
              } catch (ExpiredJwtException ex) {
                log.error("JWT token has expired");
              }
            },
            () -> log.warn("No authorization header found"));
    chain.doFilter(request, response);
  }
}
