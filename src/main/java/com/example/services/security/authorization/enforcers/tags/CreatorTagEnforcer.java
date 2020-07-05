package com.example.services.security.authorization.enforcers.tags;

import com.example.services.security.authorization.enforcers.tags.helper.CompareIdsForTag;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class CreatorTagEnforcer implements TagEnforcer {

  private static final String CREATOR = "creator";
  private static final String CREATOR_ID = "creatorId";

  @Inject
  private CompareIdsForTag compareIdsForTag;

  /**
   * The creator tag will check creator_id set in the JWT against the creator_id set in the body.
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
    return compareIdsForTag.validateRequestUrlQuery(request, claims, CREATOR_ID);
  }

  private SecurityRuleResult validateRequestBody(
      HttpRequest request, Map<String, Object> claims) {
    return compareIdsForTag.validateRequestBody(request, claims, CREATOR_ID, CREATOR);
  }

}
