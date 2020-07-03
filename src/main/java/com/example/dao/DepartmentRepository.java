package com.example.dao;

import com.example.models.entities.Department;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Maybe;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends RxJavaCrudRepository<Department, UUID> {
  Maybe<Department> findByManagerId(UUID managerId);
}
