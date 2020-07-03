package com.example.dao;

import com.example.models.entities.Employee;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Maybe;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends RxJavaCrudRepository<Employee, UUID> {
  Maybe<Employee> findByUsername(String username);

}
