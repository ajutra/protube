package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    public Video toDomain(VideoJpaEntity videoJpaEntity) {
        return new Video(
                videoJpaEntity.getVideo_id(),
                videoJpaEntity.getWidth(),
                videoJpaEntity.getHeight(),
                videoJpaEntity.getDuration(),
                videoJpaEntity.getTitle(),
                videoJpaEntity.getDescription(),
                videoJpaEntity.getUser().getUsername()
        );
    }
}
