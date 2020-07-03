package com.example.services.document;

import com.example.models.dto.DocumentDto;
import com.example.models.search.requests.DocumentSearchRequest;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DocumentService {
  Single<DocumentDto> createNewDocument(DocumentDto document);

  Single<DocumentDto> updateDocument(DocumentDto documentDto);

  Flowable<DocumentDto> getDocuments(DocumentSearchRequest documentSearchRequest);
}
