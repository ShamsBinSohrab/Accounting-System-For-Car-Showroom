package com.apiservice.multitenancy;

import com.apiservice.authentication.AuthConstants;
import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.company.Company;
import com.apiservice.service.company.CompanyService;
import com.apiservice.service.operator.OperatorService;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestInterceptor extends HandlerInterceptorAdapter {

  private final JwtTokenUtil jwtTokenUtil;
  private final OperatorService operatorService;
  private final CompanyService companyService;
  private final AuthConstants constants;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (constants.ALLOWED_PATHS.contains(request.getServletPath())
        || constants.ALLOWED_METHODS.contains(request.getMethod())) {
      return true;
    }

    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    final String token = request.getHeader(constants.tenantIdentifier);
    operatorService.findByUsername(username)
        .ifPresent(
            operator -> {
              try {
                if (StringUtils.isNotEmpty(token)) {
                  final UUID uuid = jwtTokenUtil.getIdentityFromToken(token);
                  final Company company =
                      operator.isSuperAdmin() ? companyService.getByUuid(uuid)
                          : operator.getCompany();
                  if (company.isActive()
                      && jwtTokenUtil.validateTokenIdentity(token, company.getUuid())) {
                    TenantContext.setCurrentTenant(uuid.toString());
                  } else {
                    response
                        .getWriter()
                        .write("invalid x-company-accessor-token");
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                  }
                } else {
                  if (operator.isSuperAdmin()) {
                    TenantContext.setCurrentTenant("public");
                  } else {
                    response
                        .getWriter()
                        .write("x-company-accessor-token not present in the Request Header");
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                  }
                }
              } catch (IllegalArgumentException | IOException ex) {
                log.error(ex.getMessage());
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
