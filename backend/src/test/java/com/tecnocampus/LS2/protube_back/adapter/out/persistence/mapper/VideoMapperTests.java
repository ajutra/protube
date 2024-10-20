package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
