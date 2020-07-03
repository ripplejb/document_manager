package com.example.mappers.document;

import com.example.models.dto.DocumentDto;
import com.example.models.entities.Document;

public interface DocumentMapper {
  Document fromDocumentDto(DocumentDto documentDto);

  DocumentDto toDocumentDto(Document document);

}
