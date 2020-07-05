package com.example.services.document;

import com.example.dao.DocumentRepository;
import com.example.mappers.document.DocumentMapper;
import com.example.models.dto.DocumentDto;
import com.example.models.search.requests.DocumentSearchRequest;
import io.reactivex.Flowable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DocumentServiceImpl implements DocumentService {

  @Inject private DocumentRepository documentRepository;

  @Inject private DocumentMapper documentMapper;

  @Override
  public Single<DocumentDto> createNewDocument(DocumentDto documentDto) {
    return Single.just(documentRepository
        .save(documentMapper.fromDocumentDto(documentDto)))
        .map(document -> documentMapper.toDocumentDto(document));
  }

  @Override
  public Single<DocumentDto> updateDocument(DocumentDto documentDto) {
    return Single.just(documentRepository
        .update(documentMapper.fromDocumentDto(documentDto)))
        .map(document -> documentMapper.toDocumentDto(document));
  }

  @Override
  public Flowable<DocumentDto> getDocuments(DocumentSearchRequest documentSearchRequest) {
    if (documentSearchRequest.getCreatorId() == null
        && documentSearchRequest.getDepartmentId() == null) {
      return Flowable.fromIterable(documentRepository
          .findByTitleIlike("%" + documentSearchRequest.getTitle() + "%"))
          .map(document -> documentMapper.toDocumentDto(document));
    } else if (documentSearchRequest.getCreatorId() == null) {
      return Flowable.fromIterable(documentRepository
          .findByTitleIlikeAndDepartmentId("%" + documentSearchRequest.getTitle() + "%",
              documentSearchRequest.getDepartmentId()))
          .map(document -> documentMapper.toDocumentDto(document));
    } else if (documentSearchRequest.getDepartmentId() == null) {
      return Flowable.fromIterable(documentRepository
          .findByTitleIlikeAndCreatorId("%" + documentSearchRequest.getTitle() + "%",
              documentSearchRequest.getCreatorId()))
          .map(document -> documentMapper.toDocumentDto(document));
    } else {
      return Flowable.fromIterable(documentRepository
          .findByTitleIlikeAndDepartmentIdAndCreatorId(
              "%" + documentSearchRequest.getTitle() + "%",
              documentSearchRequest.getDepartmentId(), documentSearchRequest.getCreatorId()))
          .map(document -> documentMapper.toDocumentDto(document));
    }
  }
}
