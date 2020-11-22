package com.apiservice.authentication;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final OperatorRepository operatorRepository;
  private final AuthConstants constants;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    if (constants.ALLOWED_PATHS.contains(request.getServletPath())
        || constants.ALLOWED_METHODS.contains(request.getMethod())) {
      chain.doFilter(request, response);
      return;
    }
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    Optional.ofNullable(request.getHeader("Authorization"))
        .ifPresentOrElse(
            header -> {
              try {
                final String jwtToken = header.substring(7);
                final UUID uuid = jwtTokenUtil.getIdentityFromToken(jwtToken);
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                  operatorRepository.findByUuid(uuid)
                      .ifPresentOrElse(
                          operator -> {
                            if (jwtTokenUtil.validateTokenIdentity(jwtToken, operator.getUuid())) {
                              final UserDetails details =
                                  new User(
                                      operator.getUsername(),
                                      operator.getPassword(),
                                      getAuthorities(operator));
                              final UsernamePasswordAuthenticationToken authenticationToken =
                                  new UsernamePasswordAuthenticationToken(
                                      details, null, details.getAuthorities());
                              authenticationToken.setDetails(
                                  new WebAuthenticationDetailsSource().buildDetails(request));
                              SecurityContextHolder.getContext()
                                  .setAuthentication(authenticationToken);
                              response.setStatus(HttpServletResponse.SC_OK);
                            }
                          },
                          () -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
                }
              } catch (IllegalArgumentException | ExpiredJwtException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              }
            },
            () -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
    chain.doFilter(request, response);
  }

  private List<GrantedAuthority> getAuthorities(Operator operator) {
    return Stream.of(operator.getScopes())
        .map(Scopes::valueOf)
        .collect(Collectors.toUnmodifiableList());
  }
}
