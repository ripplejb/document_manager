package com.example.services.employee;

import com.example.dao.EmployeeRepository;
import com.example.mappers.employee.EmployeeMapper;
import com.example.models.dto.EmployeeDto;
import com.example.models.dto.EmployeeRegistrationDto;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmployeeServiceImpl implements EmployeeService {

  @Inject
  private EmployeeRepository employeeRepository;

  @Inject
  private EmployeeMapper employeeMapper;

  @Override
  public Single<EmployeeDto> createNewEmployee(EmployeeRegistrationDto employeeRegistrationDto) {
    return employeeRepository.save(employeeMapper.toEmployee(employeeRegistrationDto))
        .map(employeeMapper::fromEmployee);
  }
}
