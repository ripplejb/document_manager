package com.example.services.security.authorization.enforcers;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import java.util.Map;

public interface Enforcer {
  SecurityRuleResult IsValid(HttpRequest request, Map<String, Object> claims, String policyName);
}
