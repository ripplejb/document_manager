package com.example.services.security.authorization.enforcers.tags;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class ExecutiveTagEnforcer implements TagEnforcer{

  /**
   * The executives have full access.
   * @param request incoming http request.
   * @param claims claims from the JWT.
   * @return security result (ALLOWED or REJECTED)
   */
  @Override
  public SecurityRuleResult IsValid(HttpRequest request, Map<String, Object> claims) {
    return SecurityRuleResult.ALLOWED;
  }
}
