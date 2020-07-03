package com.example.models.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Department {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "managerId")
  private Employee manager;

  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
