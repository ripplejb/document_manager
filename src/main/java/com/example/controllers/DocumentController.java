package com.example.controllers;

import com.example.models.dto.DocumentDto;
import com.example.models.search.requests.DocumentSearchRequest;
import com.example.services.document.DocumentService;
import com.example.services.security.authorization.SecurityPolicy;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller("documents")
public class DocumentController {

  @Inject
  private DocumentService documentService;

  @SecurityPolicy("document-create")
  @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public Single<HttpResponse<DocumentDto>> post(DocumentDto documentDto) {
    return documentService.createNewDocument(documentDto)
        .map(HttpResponse::created);
  }

  @SecurityPolicy("document-update")
  @Put(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public Single<HttpResponse<?>> put(DocumentDto documentDto) {
    return documentService.updateDocument(documentDto)
        .map(dd -> HttpResponse.accepted());
  }

  @SecurityPolicy("document-read")
  @Get(value = "/{?documentSearchRequest*}", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<Flowable<DocumentDto>> get(@Valid final DocumentSearchRequest documentSearchRequest) {
    return HttpResponse.ok(documentService.getDocuments(documentSearchRequest));
  }
}
