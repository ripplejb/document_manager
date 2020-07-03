package com.example.models.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Document {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "creatorId")
  private Employee creator;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "departmentId")
  private Department department;

  private String title;
  private String content;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Employee getCreator() {
    return creator;
  }

  public void setCreator(Employee creator) {
    this.creator = creator;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
