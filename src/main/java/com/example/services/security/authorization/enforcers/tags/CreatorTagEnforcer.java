package com.example.services.security.authorization.enforcers.tags;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CreatorTagEnforcer implements TagEnforcer {

  private static final String DESIGNATION = "designation";
  private static final String CREATOR = "creator";
  private static final String CREATOR_ID = "creatorId";
  private static final String SUBJECT_ID = "sub";

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
    if (request.getParameters().contains(CREATOR_ID)) {
      UUID creatorId = UUID.fromString(request.getParameters().asMap().get(CREATOR_ID).toString());
      if (creatorId.equals(UUID.fromString(claims.get(SUBJECT_ID).toString()))) {
        return SecurityRuleResult.ALLOWED;
      }
    }
    return SecurityRuleResult.REJECTED;
  }

  private SecurityRuleResult validateRequestBody(
      HttpRequest request, Map<String, Object> claims) {
    if (claims.containsKey(DESIGNATION)) {
      if (request.getBody().isEmpty()) {
        return SecurityRuleResult.REJECTED;
      }
      if (designationIsNotCreator(claims.get(DESIGNATION).toString())) {
        return SecurityRuleResult.REJECTED;
      }
      UUID creatorId = UUID.fromString(claims.get(SUBJECT_ID).toString());

      Optional<String> body = request.getBody(String.class);
      if (body.isEmpty()) {
        return SecurityRuleResult.UNKNOWN;
      }
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        JsonNode jsonNode = objectMapper.readTree(body.get());
        if (creatorId.equals(UUID.fromString(jsonNode.get(CREATOR_ID).asText()))) {
          return SecurityRuleResult.ALLOWED;
        }
      } catch (JsonProcessingException e) {
        return SecurityRuleResult.UNKNOWN;
      }

    }
    return SecurityRuleResult.REJECTED;
  }

  private Boolean designationIsNotCreator(String designation) {
    return !designation.equals(CREATOR);
  }
}
