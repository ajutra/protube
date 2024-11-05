package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.springframework.stereotype.Component;

import java.util.Set;

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
                videoJpaEntity.getUser().getUsername(),
                videoJpaEntity.getVideoFileName(),
                videoJpaEntity.getThumbnailFileName()
        );
    }

    public VideoJpaEntity toJpaEntity(
            Video video,
            UserJpaEntity userJpaEntity,
            Set<TagJpaEntity> tags,
            Set<CategoryJpaEntity> categories) {
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity();
        videoJpaEntity.setWidth(video.getWidth());
        videoJpaEntity.setHeight(video.getHeight());
        videoJpaEntity.setDuration(video.getDuration());
        videoJpaEntity.setTitle(video.getTitle());
        videoJpaEntity.setUser(userJpaEntity);
        videoJpaEntity.setDescription(video.getDescription());
        videoJpaEntity.setVideoFileName(video.getVideoFileName());
        videoJpaEntity.setThumbnailFileName(video.getThumbnailFileName());
        videoJpaEntity.setTags(tags);
        videoJpaEntity.setCategories(categories);
        return videoJpaEntity;
    }
}
