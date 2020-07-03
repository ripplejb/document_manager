package com.example.models.configuration;

import io.micronaut.context.annotation.EachProperty;

import java.util.List;

@EachProperty("authorization-policies")
public class AuthorizationPolicy {
  private String name;
  private List<String> scopes;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getScopes() {
    return scopes;
  }

  public void setScopes(List<String> scopes) {
    this.scopes = scopes;
  }
}
