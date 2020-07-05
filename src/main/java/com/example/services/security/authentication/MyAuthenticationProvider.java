package com.example.services.security.authentication;

import com.example.dao.DepartmentRepository;
import com.example.dao.EmployeeRepository;
import com.example.models.configuration.UserRole;
import com.example.models.entities.Department;
import com.example.models.entities.Employee;
import com.example.models.security.EmployeeUserDetails;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class MyAuthenticationProvider implements AuthenticationProvider {

  @Inject
  private EmployeeRepository employeeRepository;

  @Inject
  private DepartmentRepository departmentRepository;

  @Inject
  private List<UserRole> userRoles;

  /**
   * Authenticates a user with the given request. If a successful authentication is returned, the
   * object must be an instance of {@link UserDetails}.
   *
   * <p>Publishers <b>MUST emit cold observables</b>! This method will be called for all
   * authenticators for each authentication request and it is assumed no work will be done until the
   * publisher is subscribed to.
   *
   * @param httpRequest The http request
   * @param authenticationRequest The credentials to authenticate
   * @return A publisher that emits 0 or 1 responses
   */
  @Override
  public Publisher<AuthenticationResponse> authenticate(
      @Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
    return employeeRepository
        .findByUsername(authenticationRequest.getIdentity().toString())
        .defaultIfEmpty(new Employee())
        .map(employee -> getAuthenticationResponse(authenticationRequest, employee))
        .toFlowable();
  }

  private AuthenticationResponse getAuthenticationResponse(
      AuthenticationRequest<?, ?> authenticationRequest, Employee employee) {
    if (employee.getId() == null) {
      return new AuthenticationFailed("Invalid username or password");
    }
    if (employee.getPassword().equals(authenticationRequest.getSecret())) {
      Optional<UserRole> userRoleOptional =
          userRoles.stream()
              .filter(userRole -> userRole.getName().equals(employee.getDesignation()))
              .findFirst();
      if (userRoleOptional.isEmpty()) {
        return new AuthenticationFailed("User do not have enough privileges");
      }
      return getAuthenticationResponseBasedOnDesignation(employee, userRoleOptional.get());
    }
    return new AuthenticationFailed("Invalid username or password");
  }

  private AuthenticationResponse getAuthenticationResponseBasedOnDesignation(Employee employee, UserRole userRole) {
    if (employee.getDesignation().equals("manager")) {
      return getManagerAuthenticationResponse(employee, userRole);
    }
    return getEmployeeUserDetails(employee.getId(), employee, userRole);
  }

  private AuthenticationResponse getManagerAuthenticationResponse(Employee employee, UserRole userRole) {
    return departmentRepository.findByManagerId(employee.getId())
        .defaultIfEmpty(new Department()).map(dept -> {
          if (dept.getId() == null) {
            return new AuthenticationFailed("The manager do not have assigned department.");
          }
          return getEmployeeUserDetails(dept.getId(), employee, userRole);
        }).blockingGet();
  }

  private EmployeeUserDetails getEmployeeUserDetails(UUID userDetailId, Employee employee, UserRole userRole) {
    EmployeeUserDetails employeeUserDetails = new EmployeeUserDetails(userDetailId.toString(),
        userRole.getScopes());
    Map<String, Object> claims = new HashMap<>();
    claims.put("designation", employee.getDesignation());
    employeeUserDetails.setClaims(claims);
    return employeeUserDetails;
  }
}
