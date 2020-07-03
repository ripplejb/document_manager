package com.example.services.department;

import com.example.dao.DepartmentRepository;
import com.example.mappers.department.DepartmentMapper;
import com.example.models.dto.DepartmentDto;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DepartmentServiceImpl implements DepartmentService{

  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private DepartmentMapper departmentMapper;

  @Override
  public Single<DepartmentDto> createNewDepartment(DepartmentDto departmentDto) {
    return departmentRepository.save(departmentMapper.toDepartment(departmentDto))
        .map(departmentMapper::fromDepartment);
  }
}
