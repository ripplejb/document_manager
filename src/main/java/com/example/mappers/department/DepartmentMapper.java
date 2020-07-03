package com.example.mappers.department;

import com.example.models.dto.DepartmentDto;
import com.example.models.entities.Department;

public interface DepartmentMapper {
  DepartmentDto fromDepartment(Department department);
  Department toDepartment(DepartmentDto departmentDto);
}
