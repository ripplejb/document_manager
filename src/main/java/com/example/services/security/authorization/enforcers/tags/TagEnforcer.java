package com.example.services.security.authorization.enforcers.tags;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import java.util.Map;

public interface TagEnforcer {
  SecurityRuleResult IsValid(HttpRequest request, Map<String, Object> claims);
}
