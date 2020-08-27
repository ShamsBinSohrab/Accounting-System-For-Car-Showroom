package com.apiservice.multitenancy;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (request.getServletPath().equalsIgnoreCase("/authenticate")) {
      return true;
    }
    Optional.ofNullable(request.getHeader("X-Tenant-Accessor-Token"))
        .ifPresentOrElse(
            TenantContext::setCurrentTenant,
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
