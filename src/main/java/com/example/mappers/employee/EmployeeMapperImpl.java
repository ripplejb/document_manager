package com.example.mappers.employee;

import com.example.models.dto.EmployeeDto;
import com.example.models.dto.EmployeeRegistrationDto;
import com.example.models.entities.Employee;

import javax.inject.Singleton;

@Singleton
public class EmployeeMapperImpl implements EmployeeMapper {
  @Override
  public EmployeeDto fromEmployee(Employee employee) {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setDesignation(employee.getDesignation());
    employeeDto.setName(employee.getName());
    employeeDto.setId(employee.getId());
    return employeeDto;
  }

  @Override
  public Employee toEmployee(EmployeeRegistrationDto employeeRegistrationDto) {
    Employee employee = new Employee();
    employee.setId(employeeRegistrationDto.getId());
    employee.setDesignation(employeeRegistrationDto.getDesignation());
    employee.setName(employeeRegistrationDto.getName());
    employee.setUsername(employeeRegistrationDto.getUsername());
    employee.setPassword(employeeRegistrationDto.getPassword());
    return employee;
  }
}
