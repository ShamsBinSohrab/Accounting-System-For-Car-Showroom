package com.apiservice.authentication;

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
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class RequestFilter extends OncePerRequestFilter {

  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    if (request.getServletPath().equalsIgnoreCase("/authenticate")
        || request.getMethod().equalsIgnoreCase("OPTIONS")) {
      chain.doFilter(request, response);
      return;
    }
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    Optional.ofNullable(request.getHeader("Authorization"))
        .ifPresentOrElse(
            header -> {
              try {
                final String jwtToken = header.substring(7);
                final String username = jwtTokenUtil.getSubjectFromToken(jwtToken);
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                  final UserDetails details = userDetailsServiceImpl.loadUserByUsername(username);
                  if (jwtTokenUtil.validateAuthToken(jwtToken, details)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            details, null, details.getAuthorities());
                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    response.setStatus(HttpServletResponse.SC_OK);
                  }
                }
              } catch (IllegalArgumentException | ExpiredJwtException ex) {
                log.error(ex.getMessage());
                throw new AuthenticationException(ex.getCause());
              }
            },
            () -> {
              throw new AuthenticationException();
            });
    chain.doFilter(request, response);
  }
}
