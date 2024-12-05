package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MongoVideoMapper {
    public VideoDocument toDocument(Video video, Set<Tag> tags, Set<Category> categories) {
        return new VideoDocument(
                video.getId(),
                video.getTitle(),
                video.getUsername(),
                tags.stream().map(Tag::name).collect(java.util.stream.Collectors.toSet()),
                categories.stream().map(Category::name).collect(java.util.stream.Collectors.toSet())
        );
    }

    public Video toDomain(VideoDocument videoDocument) {
        Video video = new Video();
        video.setId(videoDocument.getId());
        video.setTitle(videoDocument.getTitle());
        video.setUsername(videoDocument.getUsername());
        return video;
    }
}
