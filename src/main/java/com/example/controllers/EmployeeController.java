package com.example.controllers;

import com.example.models.dto.EmployeeDto;
import com.example.models.dto.EmployeeRegistrationDto;
import com.example.services.employee.EmployeeService;
import com.example.services.security.authorization.SecurityPolicy;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;

import javax.inject.Inject;

@Controller
public class EmployeeController {

  @Inject
  private EmployeeService employeeService;

  @SecurityPolicy("employee-create")
  @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  Single<HttpResponse<EmployeeDto>> post(EmployeeRegistrationDto employeeRegistrationDto) {
    return employeeService.createNewEmployee(employeeRegistrationDto)
        .map(HttpResponse::ok);
  }

}
