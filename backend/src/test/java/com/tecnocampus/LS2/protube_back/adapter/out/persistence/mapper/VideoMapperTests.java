package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoMapperTests {

    private final VideoMapper videoMapper = new VideoMapper();

    @Test
    void mapVideoJpaEntityToDomain() {
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity(
                "1",
                1920,
                1080,
                300,
                "Title 1",
                "Description 1",
                new UserJpaEntity("user_id1", "username1"),
                new HashSet<>(),
                new HashSet<>());

        Video video = videoMapper.toDomain(videoJpaEntity);

        assertEquals("1", video.id());
        assertEquals(1920, video.width());
        assertEquals(1080, video.height());
        assertEquals(300, video.duration());
        assertEquals("Title 1", video.title());
        assertEquals("Description 1", video.description());
        assertEquals("user_id1", video.userId());
    }
}
