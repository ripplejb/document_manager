package com.example.services.employee;

import com.example.models.dto.EmployeeDto;
import com.example.models.dto.EmployeeRegistrationDto;
import io.reactivex.Single;

public interface EmployeeService {
  Single<EmployeeDto> createNewEmployee(EmployeeRegistrationDto employeeRegistrationDto);
}
