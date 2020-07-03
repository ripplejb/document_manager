package com.example.mappers.employee;

import com.example.models.dto.EmployeeDto;
import com.example.models.dto.EmployeeRegistrationDto;
import com.example.models.entities.Employee;

public interface EmployeeMapper {
  EmployeeDto fromEmployee(Employee employee);
  Employee toEmployee(EmployeeRegistrationDto employeeRegistrationDto);
}
