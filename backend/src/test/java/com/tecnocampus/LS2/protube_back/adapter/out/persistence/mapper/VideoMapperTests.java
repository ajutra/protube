package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoMapperTests {

    private final VideoMapper videoMapper = new VideoMapper();

    @Test
    void mapVideoJpaEntityToDomain() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        Video video = videoMapper.toDomain(videoJpaEntity);

        assertEquals(videoJpaEntity.getVideo_id(), video.id());
        assertEquals(videoJpaEntity.getWidth(), video.width());
        assertEquals(videoJpaEntity.getHeight(), video.height());
        assertEquals(videoJpaEntity.getDuration(), video.duration());
        assertEquals(videoJpaEntity.getTitle(), video.title());
        assertEquals(videoJpaEntity.getDescription(), video.description());
        assertEquals(videoJpaEntity.getVideoFileName(), video.videoFileName());
        assertEquals(videoJpaEntity.getThumbnailFileName(), video.thumbnailFileName());
        assertEquals(videoJpaEntity.getUser().getUsername(), video.username());
    }

    @Test
    void mapVideoToJpaEntity() {
        Set<String> ids = Set.of("1", "2", "3");

        Video video = TestObjectFactory.createDummyVideo("1");
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("1");

        Set<TagJpaEntity> tags = new HashSet<>();
        Set<CategoryJpaEntity> categories = new HashSet<>();
        ids.forEach(id -> {
            tags.add(TestObjectFactory.createDummyTagJpaEntity(id));
            categories.add(TestObjectFactory.createDummyCategoryJpaEntity(id));
        });

        VideoJpaEntity videoJpaEntity = videoMapper.toJpaEntity(video, userJpaEntity, tags, categories);

        assertEquals(video.width(), videoJpaEntity.getWidth());
        assertEquals(video.height(), videoJpaEntity.getHeight());
        assertEquals(video.duration(), videoJpaEntity.getDuration());
        assertEquals(video.title(), videoJpaEntity.getTitle());
        assertEquals(video.description(), videoJpaEntity.getDescription());
        assertEquals(video.videoFileName(), videoJpaEntity.getVideoFileName());
        assertEquals(video.thumbnailFileName(), videoJpaEntity.getThumbnailFileName());
        assertEquals(video.username(), videoJpaEntity.getUser().getUsername());

        assertEquals(tags.size(), videoJpaEntity.getTags().size());
        tags.forEach(tag -> assertTrue(videoJpaEntity.getTags().stream()
                .anyMatch(tagJpaEntity -> tagJpaEntity.getTag_name().equals(tag.getTag_name()))));

        assertEquals(categories.size(), videoJpaEntity.getCategories().size());
        categories.forEach(category -> assertTrue(videoJpaEntity.getCategories().stream()
                .anyMatch(categoryJpaEntity -> categoryJpaEntity.getCategory_name().equals(category.getCategory_name()))));
    }
}
