package com.example.models.security;

import io.micronaut.security.authentication.UserDetails;

import java.util.Collection;
import java.util.Map;

public class EmployeeUserDetails extends UserDetails {

  private Map<String, Object> claims;

  /**
   * @param username e.g. admin
   * @param scopes   e.g. ['document.full', 'team:document.readonly']
   */
  public EmployeeUserDetails(String username, Collection<String> scopes) {
    super(username, scopes);
  }

  /**
   * @param username   e.g. admin
   * @param scopes     e.g. ['document.full', 'team:document.readonly']
   * @param attributes User's attributes
   */
  public EmployeeUserDetails(String username, Collection<String> scopes, Map<String, Object> attributes) {
    super(username, scopes, attributes);
  }

  public Map<String, Object> getClaims() {
    return claims;
  }

  public void setClaims(Map<String, Object> claims) {
    this.claims = claims;
  }
}
