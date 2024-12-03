package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.mapper.MongoVideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository.MongoVideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Qualifier("mongoStoreVideoPort")
public class MongoVideoPersistenceAdapter implements StoreVideoPort {
    private final MongoVideoRepository mongoVideoRepository;
    private final MongoVideoMapper mongoVideoMapper;

    @Override
    public void storeVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        storeAndGetVideo(video, tags, categories);
    }

    @Override
    public Video storeAndGetVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        VideoDocument videoDocument = mongoVideoMapper.toDocument(video, tags, categories);

        Optional <VideoDocument> savedDocument = mongoVideoRepository.findVideoDocumentByTitleEqualsAndUsernameEquals(
                videoDocument.getTitle(),
                videoDocument.getUsername());

        // If the video already exists, delete it, this is an update operation when loading initial data on dev profile
        savedDocument.ifPresent(mongoVideoRepository::delete);

        mongoVideoRepository.save(videoDocument);

        return mongoVideoMapper.toDomain(videoDocument);
    }
}
