package com.example.models.search.requests;

import com.sun.istack.Nullable;
import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Introspected
public class DocumentSearchRequest {
  @Nullable
  private String title;
  @Nullable
  private UUID creatorId;
  @Nullable
  private UUID departmentId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UUID getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(UUID creatorId) {
    this.creatorId = creatorId;
  }

  public UUID getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(UUID departmentId) {
    this.departmentId = departmentId;
  }
}
