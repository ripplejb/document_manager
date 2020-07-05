package com.example.services.security.authorization.enforcers.tags;

import com.example.services.security.authorization.enforcers.tags.helper.CompareIdsForSecurityTagHelper;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class ManagerTagEnforcer implements TagEnforcer {

  private static final String MANAGER = "manager";
  private static final String DEPARTMENT_ID = "departmentId";

  @Inject
  private CompareIdsForSecurityTagHelper compareIdsForSecurityTagHelper;

  /**
   * The manager tag will check department_id set in the JWT against the department_id set in the body.
   * @param request incoming http request.
   * @param claims claims from the JWT.
   * @return security result (ALLOWED or REJECTED)
   */
  @Override
  public SecurityRuleResult IsValid(HttpRequest request, Map<String, Object> claims) {

    // TODO: Check request body for PUT and POST
    if (request.getMethod().equals(HttpMethod.POST)
        || request.getMethod().equals(HttpMethod.PUT)) {
      return validateRequestBody(request, claims);
    }

    // TODO: Check request url for GET
    return validateRequestUrlQuery(request, claims);
  }

  private SecurityRuleResult validateRequestUrlQuery(
      HttpRequest request, Map<String, Object> claims) {
    return compareIdsForSecurityTagHelper.validateRequestUrlQuery(request, claims, DEPARTMENT_ID);
  }

  private SecurityRuleResult validateRequestBody(
      HttpRequest request, Map<String, Object> claims) {
    return compareIdsForSecurityTagHelper.validateRequestBody(request, claims, DEPARTMENT_ID, MANAGER);
  }

}
