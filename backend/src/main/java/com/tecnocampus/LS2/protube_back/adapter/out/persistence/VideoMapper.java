package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    Video toDomain(VideoJpaEntity videoJpaEntity) {
        return new Video(
                videoJpaEntity.getId(),
                videoJpaEntity.getTitle());
    }
}
