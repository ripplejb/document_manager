package com.example.services.department;

import com.example.models.dto.DepartmentDto;
import io.reactivex.Single;

public interface DepartmentService {
  Single<DepartmentDto> createNewDepartment(DepartmentDto departmentDto);
}
