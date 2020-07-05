package com.example.services.security.authorization.enforcers.tags.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.rules.SecurityRuleResult;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class CompareIdsHelperImpl implements CompareIdsForTag{

  private static final String SUBJECT_ID = "sub";
  private static final String DESIGNATION = "designation";

  @Override
  public SecurityRuleResult validateRequestUrlQuery(HttpRequest request, Map<String, Object> claims, String idType) {
    if (request.getParameters().contains(idType)) {
      UUID id = UUID.fromString(request.getParameters().asMap(String.class, String.class).get(idType));
      if (id.equals(UUID.fromString(claims.get(SUBJECT_ID).toString()))) {
        return SecurityRuleResult.ALLOWED;
      }
    }
    return SecurityRuleResult.REJECTED;
  }

  @Override
  public SecurityRuleResult validateRequestBody(HttpRequest request, Map<String, Object> claims,
                                                String idType, String designationValue) {
    if (claims.containsKey(DESIGNATION)) {
      if (request.getBody().isEmpty()) {
        return SecurityRuleResult.REJECTED;
      }
      if (designationIsNotCreator(claims.get(DESIGNATION).toString(), designationValue)) {
        return SecurityRuleResult.REJECTED;
      }
      UUID subjectId = UUID.fromString(claims.get(SUBJECT_ID).toString());

      Optional<String> body = request.getBody(String.class);
      if (body.isEmpty()) {
        return SecurityRuleResult.UNKNOWN;
      }
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        JsonNode jsonNode = objectMapper.readTree(body.get());
        if (subjectId.equals(UUID.fromString(jsonNode.get(idType).asText()))) {
          return SecurityRuleResult.ALLOWED;
        }
      } catch (JsonProcessingException e) {
        return SecurityRuleResult.UNKNOWN;
      }

    }
    return SecurityRuleResult.REJECTED;
  }

  private Boolean designationIsNotCreator(String designation, String designationType) {
    return !designation.equals(designationType);
  }
}
