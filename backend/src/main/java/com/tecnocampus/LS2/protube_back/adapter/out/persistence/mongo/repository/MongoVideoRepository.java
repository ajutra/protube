package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoVideoRepository extends MongoRepository<VideoDocument, String> {
    Optional<VideoDocument> findVideoDocumentByTitleEqualsAndUsernameEquals(String title, String username);
}
