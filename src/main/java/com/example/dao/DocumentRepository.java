package com.example.dao;

import com.example.models.entities.Document;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;

import javax.annotation.Nullable;
import java.util.UUID;

@Repository
public interface DocumentRepository extends RxJavaCrudRepository<Document, UUID> {
  Flowable<Document> findByTitleIlikeAndCreatorId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID creatorId);

  Flowable<Document> findByTitleIlikeAndDepartmentId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID departmentId);

  Flowable<Document> findByTitleIlikeAndDepartmentIdAndCreatorId(
      @Nullable String titleQueryCaseIgnored, @Nullable UUID departmentId, @Nullable UUID creatorId);

  Flowable<Document> findByTitleIlike(String titleQueryCaseIgnored);

  Single<Document> update(Document document);
}
