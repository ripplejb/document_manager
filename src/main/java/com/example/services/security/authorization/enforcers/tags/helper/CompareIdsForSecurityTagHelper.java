package com.example.services.security.authorization.enforcers.tags.helper;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import java.util.Map;

public interface CompareIdsForSecurityTagHelper {
  SecurityRuleResult validateRequestUrlQuery(
      HttpRequest request, Map<String, Object> claims, String idType);
  SecurityRuleResult validateRequestBody(
      HttpRequest request, Map<String, Object> claims, String idType,
      String designationValue);
}
