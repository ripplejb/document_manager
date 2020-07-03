package com.example.controllers;

import com.example.models.dto.DocumentDto;
import com.example.services.document.DocumentService;
import com.example.services.security.authorization.SecurityPolicy;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;

import javax.inject.Inject;

@Controller("documents")
public class DocumentController {

  @Inject
  private DocumentService documentService;

  @SecurityPolicy("document-create")
  @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public Single<HttpResponse<DocumentDto>> post(DocumentDto documentDto) {
    return documentService.createNewDocument(documentDto)
        .map(HttpResponse::ok);
  }

}
