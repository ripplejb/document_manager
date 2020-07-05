package com.example.mappers.document;

import com.example.models.dto.DocumentDto;
import com.example.models.entities.Department;
import com.example.models.entities.Document;
import com.example.models.entities.Employee;

import javax.inject.Singleton;

@Singleton
public class DocumentMapperImpl implements DocumentMapper {

  @Override
  public Document fromDocumentDto(DocumentDto documentDto) {
    Document document = new Document();
    if (documentDto == null) {
      return document;
    }
    document.setContent(documentDto.getContent());
    document.setId(documentDto.getId());
    document.setTitle(documentDto.getTitle());

    Employee employee = new Employee();
    employee.setId(documentDto.getCreatorId());
    document.setCreator(employee);

    Department department = new Department();
    department.setId(documentDto.getDepartmentId());
    document.setDepartment(department);
    return document;
  }

  @Override
  public DocumentDto toDocumentDto(Document document) {
    DocumentDto documentDto = new DocumentDto();
    if (document == null) {
      return documentDto;
    }
    documentDto.setContent(document.getContent());
    documentDto.setTitle(document.getTitle());
    documentDto.setId(document.getId());
    documentDto.setCreatorId(document.getCreator().getId());
    documentDto.setDepartmentId(document.getDepartment().getId());
    return documentDto;
  }
}
