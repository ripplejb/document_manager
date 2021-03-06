package com.example.models.dto;

import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Introspected
public class EmployeeDto {
  private UUID id;
  private String name;
  private String designation;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }
}
