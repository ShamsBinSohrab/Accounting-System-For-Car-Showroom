package com.apiservice.multitenancy;

import static java.util.Objects.nonNull;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.operator.OperatorRole;
import com.apiservice.service.operator.OperatorService;
import com.apiservice.utils.Constants;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class RequestInterceptor extends HandlerInterceptorAdapter {

  private final JwtTokenUtil jwtTokenUtil;
  private final OperatorService operatorService;
  private final Constants constants;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (request.getServletPath().equalsIgnoreCase("/authenticate")) {
      return true;
    }

    final Optional<String> token =
        Optional.ofNullable(request.getHeader("x-company-accessor-token"));
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    final Operator operator = operatorService.getByUsername(username);

    if (token.isPresent()) {
      if (operator.getRole().equals(OperatorRole.SUPER_ADMIN)
          || (nonNull(operator.getCompany())
              && jwtTokenUtil.validateTenantToken(token.get(), operator.getCompany()))) {
        final String tenant = jwtTokenUtil.getSubjectFromToken(token.get());
        TenantContext.setCurrentTenant(tenant);
      }
    } else if (operator.getRole().equals(OperatorRole.SUPER_ADMIN)) {
      TenantContext.setCurrentTenant(constants.DEFAULT_COMPANY_IDENTIFIER);
    } else {
      try {
        response
            .getWriter()
            .write("X-Tenant-Accessor-Token not present in the Request Header");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
      } catch (IOException ex) {
        log.error(ex.getMessage(), ex);
      }
    }
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
