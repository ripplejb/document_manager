package com.example.services.security.authorization;

import com.example.services.security.authorization.enforcers.Enforcer;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.ConfigurationInterceptUrlMapRule;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.web.router.MethodBasedRouteMatch;
import io.micronaut.web.router.RouteMatch;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MySecurityRule implements SecurityRule {

  /** The order of the rule. */
  private static final Integer ORDER = ConfigurationInterceptUrlMapRule.ORDER - 100;
  @Inject
  @Named("policy")
  private Enforcer enforcer;

  private static final String DENY_ALL = "deny-all";
  private static final String ALLOW_ALL = "allow-all";


  /**
   * Returns a security result based on any conditions.
   *
   * @param request The current request
   * @param routeMatch The matched route or empty if no route was matched. e.g. static resource.
   * @param claims The claims from the token. Null if not authenticated
   * @return The result
   * @see SecurityRuleResult
   */
  @Override
  public SecurityRuleResult check(
      HttpRequest<?> request,
      @Nullable RouteMatch<?> routeMatch,
      @Nullable Map<String, Object> claims) {
    if (routeMatch instanceof MethodBasedRouteMatch) {
      MethodBasedRouteMatch methodRoute = ((MethodBasedRouteMatch) routeMatch);
      if (methodRoute.hasAnnotation(SecurityPolicy.class)) {
        Optional<String> optionalValue = methodRoute.getValue(SecurityPolicy.class, String.class);
        if (optionalValue.isPresent()) {
          String policyName = optionalValue.get();
          if (policyName.contains(DENY_ALL)) {
            return SecurityRuleResult.REJECTED;
          }
          if (policyName.contains(ALLOW_ALL)) {
            return SecurityRuleResult.ALLOWED;
          }
          if (claims == null) {
            return SecurityRuleResult.REJECTED;
          }

          return enforcer.IsValid(request, claims, policyName);
        }
      }
    }
    return SecurityRuleResult.UNKNOWN;
  }

  /** @return The order of the object. Defaults to zero (no order). */
  @Override
  public int getOrder() {
    return ORDER;
  }
}
