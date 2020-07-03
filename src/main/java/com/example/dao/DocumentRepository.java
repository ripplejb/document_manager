package com.example.dao;

import com.example.models.entities.Document;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.UUID;

@Repository
public interface DocumentRepository extends RxJavaCrudRepository<Document, UUID> {
  Flowable<Document> findByTitleIlikeAndCreatorId(
      String titleQueryCaseIgnored, UUID creatorId);

  Flowable<Document> findByTitleIlikeAndDepartmentId(
      String titleQueryCaseIgnored, UUID departmentId);

  Flowable<Document> findByTitleIlikeAndDepartmentIdAndCreatorId(
      String titleQueryCaseIgnored, UUID departmentId, UUID creatorId);

  Flowable<Document> findByTitleIlike(String titleQueryCaseIgnored);

  Single<Document> update(Document document);
}
