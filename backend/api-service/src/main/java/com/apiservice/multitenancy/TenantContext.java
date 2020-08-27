package com.apiservice.multitenancy;

public class TenantContext {
  private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

  public static String getCurrentTenant() {
    return currentTenant.get();
  }

  public static void setCurrentTenant(String tenant) {
    currentTenant.set(tenant);
  }

  public static void clear() {
    currentTenant.set(null);
  }

  public static boolean isTenantSet() {
    return currentTenant.get() != null;
  }
}