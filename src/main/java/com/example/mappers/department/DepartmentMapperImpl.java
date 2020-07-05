package com.example.mappers.department;

import com.example.models.dto.DepartmentDto;
import com.example.models.entities.Department;
import com.example.models.entities.Employee;

import javax.inject.Singleton;

@Singleton
public class DepartmentMapperImpl implements DepartmentMapper{
  @Override
  public DepartmentDto fromDepartment(Department department) {
    DepartmentDto departmentDto = new DepartmentDto();
    if (department == null) {
      return departmentDto;
    }
    departmentDto.setId(department.getId());
    departmentDto.setManagerId(department.getManager().getId());
    departmentDto.setName(department.getName());
    return departmentDto;
  }

  @Override
  public Department toDepartment(DepartmentDto departmentDto) {
    Department department = new Department();
    if (departmentDto == null) {
      return department;
    }
    department.setId(departmentDto.getId());
    department.setName(departmentDto.getName());

    Employee manager = new Employee();
    manager.setId(departmentDto.getManagerId());
    department.setManager(manager);

    return department;
  }
}
