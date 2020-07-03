package com.example.controllers;

import com.example.models.dto.DepartmentDto;
import com.example.services.department.DepartmentService;
import com.example.services.security.authorization.SecurityPolicy;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;

import javax.inject.Inject;

@Controller("departments")
public class DepartmentController {

  @Inject
  private DepartmentService departmentService;

  @SecurityPolicy("department-create")
  @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public Single<HttpResponse<DepartmentDto>> post(DepartmentDto departmentDto) {
    return departmentService.createNewDepartment(departmentDto)
        .map(HttpResponse::ok);
  }

}
