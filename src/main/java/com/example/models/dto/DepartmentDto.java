package com.example.models.dto;

import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Introspected
public class DepartmentDto {
  private UUID id;
  private UUID managerId;
  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getManagerId() {
    return managerId;
  }

  public void setManagerId(UUID managerId) {
    this.managerId = managerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
