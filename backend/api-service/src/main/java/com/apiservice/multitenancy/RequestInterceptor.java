package com.apiservice.multitenancy;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.company.Company;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestInterceptor extends HandlerInterceptorAdapter {

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (request.getServletPath().equalsIgnoreCase("/authenticate")) {
      return true;
    }
    Optional.ofNullable(request.getHeader("x-company-accessor-token"))
        .ifPresentOrElse(
            token -> {

              Company company = new Company();
              company.setUuid(UUID.fromString("2454665b-5190-4833-903f-2f9264895bdd"));
              log.debug("Token valid: {}", jwtTokenUtil.validateTenantToken(token, company));
              String uuid = jwtTokenUtil.getSubjectFromToken(token);
              log.debug("Tenant uuid: {}", uuid);
              TenantContext.setCurrentTenant(uuid);
            },
            () -> {
              try {
                response.getWriter().write("X-Tenant-Accessor-Token not present in the Request Header");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
              } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
              }
            });
    return TenantContext.isTenantSet();
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {
    TenantContext.clear();
  }
}
