package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.VideoMapper;
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

        assertEquals(videoJpaEntity.getVideoId(), video.getId());
        assertEquals(videoJpaEntity.getWidth(), video.getWidth());
        assertEquals(videoJpaEntity.getHeight(), video.getHeight());
        assertEquals(videoJpaEntity.getDuration(), video.getDuration());
        assertEquals(videoJpaEntity.getTitle(), video.getTitle());
        assertEquals(videoJpaEntity.getDescription(), video.getDescription());
        assertEquals(videoJpaEntity.getVideoFileName(), video.getVideoFileName());
        assertEquals(videoJpaEntity.getThumbnailFileName(), video.getThumbnailFileName());
        assertEquals(videoJpaEntity.getUser().getUsername(), video.getUsername());
        assertEquals(videoJpaEntity.getUserVideoLikes().stream().filter(UserVideoLikeJpaEntity::isHasLiked).count(), video.getLikes());
        assertEquals(videoJpaEntity.getUserVideoLikes().stream().filter(UserVideoLikeJpaEntity::isHasDisliked).count(), video.getDislikes());
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

        assertEquals(video.getWidth(), videoJpaEntity.getWidth());
        assertEquals(video.getHeight(), videoJpaEntity.getHeight());
        assertEquals(video.getDuration(), videoJpaEntity.getDuration());
        assertEquals(video.getTitle(), videoJpaEntity.getTitle());
        assertEquals(video.getDescription(), videoJpaEntity.getDescription());
        assertEquals(video.getVideoFileName(), videoJpaEntity.getVideoFileName());
        assertEquals(video.getThumbnailFileName(), videoJpaEntity.getThumbnailFileName());
        assertEquals(video.getUsername(), videoJpaEntity.getUser().getUsername());

        assertEquals(tags.size(), videoJpaEntity.getTags().size());
        tags.forEach(tag -> assertTrue(videoJpaEntity.getTags().stream()
                .anyMatch(tagJpaEntity -> tagJpaEntity.getTag_name().equals(tag.getTag_name()))));

        assertEquals(categories.size(), videoJpaEntity.getCategories().size());
        categories.forEach(category -> assertTrue(videoJpaEntity.getCategories().stream()
                .anyMatch(categoryJpaEntity -> categoryJpaEntity.getCategory_name().equals(category.getCategory_name()))));
    }
}
