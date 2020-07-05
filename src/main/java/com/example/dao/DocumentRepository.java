package com.example.dao;

import com.example.models.entities.Document;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends CrudRepository<Document, UUID> {
  List<Document> findByTitleIlikeAndCreatorId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID creatorId);

  List<Document> findByTitleIlikeAndDepartmentId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID departmentId);

  List<Document> findByTitleIlikeAndDepartmentIdAndCreatorId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID departmentId, @Nullable UUID creatorId);

  List<Document> findByTitleIlike(String titleQueryCaseIgnored);

  Document update(Document document);
}
