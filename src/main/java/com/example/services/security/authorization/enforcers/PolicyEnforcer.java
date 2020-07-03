package com.example.services.security.authorization.enforcers;

import com.example.models.configuration.AuthorizationPolicy;
import com.example.services.security.authorization.enforcers.tags.TagEnforcer;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class PolicyEnforcer implements Enforcer {

  private static final String JWT_SCOPES_KEY = "scopes";
  private static final String DENY_ALL = "deny-all";
  private static final String ALLOW_ALL = "allow-all";


  @Inject private List<AuthorizationPolicy> policies;
  @Inject private List<TagEnforcer> tagEnforcers;

  @Override
  public SecurityRuleResult IsValid(
      HttpRequest request, Map<String, Object> claims, String policyName) {
    List<String> policyScopes = getPolicyScopes(policyName);

    if (IsAllowAll(policyScopes)) {
      return SecurityRuleResult.ALLOWED;
    }

    if (IsDenyAll(policyScopes)) {
      return SecurityRuleResult.REJECTED;
    }

    if (claims == null) {
      return SecurityRuleResult.UNKNOWN;
    }

    if (claims.containsKey(JWT_SCOPES_KEY)) {

      List<String> jwtScopes = getJWTScopes(claims);

      if (jwtScopes.isEmpty()) {
        return SecurityRuleResult.REJECTED;
      }

      List<String> userScopes = getUserScopes(policyScopes, jwtScopes);

      if (userScopes.isEmpty()) {
        return SecurityRuleResult.REJECTED;
      }

      return enforceTag(request, claims, policyName, userScopes.get(0));
    }

    return SecurityRuleResult.REJECTED;
  }

  private Boolean IsDenyAll(List<String> jwtScopes) {
    return jwtScopes.get(0).equals(DENY_ALL);
  }

  private Boolean IsAllowAll(List<String> jwtScopes) {
    return jwtScopes.get(0).equals(ALLOW_ALL);
  }

  private List<String> getJWTScopes(Map<String, Object> claims) {
    if (claims.get(JWT_SCOPES_KEY) instanceof ArrayList) {
      try {
        return (ArrayList<String>) claims.get(JWT_SCOPES_KEY);
      } catch (RuntimeException exception) {
        return new ArrayList<>();
      }
    } else {
      return new ArrayList<>();
    }
  }

  private List<String> getPolicyScopes(String policyName) {
    return policies.stream()
        .filter(p -> p.getName().equals(policyName))
        .findFirst()
        .map(AuthorizationPolicy::getScopes)
        .orElse(new ArrayList<>());
  }

  private List<String> getUserScopes(List<String> policyScopes, List<String> jwtScopes) {
    return policyScopes.stream()
        .filter(scope -> jwtScopes.stream().anyMatch(s -> s.equals(scope)))
        .collect(Collectors.toList());
  }

  private Optional<TagEnforcer> getTagEnforcer(String tag) {
    String finalTag = tag;
    return tagEnforcers.stream()
        .filter(tagEnforcer -> tagEnforcer.getClass().getName().startsWith(finalTag))
        .findFirst();
  }

  private SecurityRuleResult enforceTag(
      HttpRequest request, Map<String, Object> claims, String policyName, String scope) {
    String[] tags = scope.split(":", 2);
    String tag;
    if (tags.length > 1) {
      tag = tags[0];
    } else {
      tag = "No";
    }
    Optional<TagEnforcer> tagEnforcerOptional = getTagEnforcer(tag);

    if (tagEnforcerOptional.isEmpty()) {
      return SecurityRuleResult.ALLOWED;
    }

    return tagEnforcerOptional.get().IsValid(request, claims);
  }
}
