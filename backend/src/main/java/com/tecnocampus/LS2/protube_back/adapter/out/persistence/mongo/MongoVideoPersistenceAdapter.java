package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.mapper.MongoVideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository.MongoVideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.SearchVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Qualifier("mongoVideoPort")
public class MongoVideoPersistenceAdapter implements StoreVideoPort, SearchVideoPort, DeleteVideoPort {
    private final MongoVideoRepository mongoVideoRepository;
    private final MongoVideoMapper mongoVideoMapper;
    private final MongoTemplate mongoTemplate;

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

    @Override
    public void editVideo(Video video) {
        mongoVideoRepository.findById(video.getId())
                .ifPresentOrElse(
                        videoDocument -> {
                            videoDocument.setTitle(video.getTitle());

                            mongoVideoRepository.save(videoDocument);
                            },

                        () -> {
                            throw new NoSuchElementException("Video with id " + video.getId() + " not found on MongoDB");
                });
    }

    @Override
    public List<Video> searchVideos(String text) {
        TextQuery textQuery = TextQuery.queryText(new TextCriteria().matchingAny(text)).sortByScore();
        List<VideoDocument> videoDocuments = mongoTemplate.find(textQuery, VideoDocument.class, "video");

        return videoDocuments.stream()
                .map(mongoVideoMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteVideo(String videoId) {
        mongoVideoRepository.deleteById(videoId);
    }
}
