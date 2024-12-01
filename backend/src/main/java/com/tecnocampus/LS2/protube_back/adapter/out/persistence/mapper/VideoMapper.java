package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class VideoMapper {
    public Video toDomain(VideoJpaEntity videoJpaEntity) {
        AtomicInteger likes = new AtomicInteger(0);
        AtomicInteger dislikes = new AtomicInteger(0);

        if (videoJpaEntity.getUserVideoLikes() != null) {
            videoJpaEntity.getUserVideoLikes().forEach(userVideoLikeJpaEntity -> {
                if (userVideoLikeJpaEntity.isHasLiked())
                    likes.getAndSet(likes.get() + 1);
            });

            videoJpaEntity.getUserVideoLikes().forEach(userVideoLikeJpaEntity -> {
                if (userVideoLikeJpaEntity.isHasDisliked())
                    dislikes.getAndSet(dislikes.get() + 1);
            });
        }

        return new Video(
                videoJpaEntity.getVideoId(),
                videoJpaEntity.getWidth(),
                videoJpaEntity.getHeight(),
                videoJpaEntity.getDuration(),
                videoJpaEntity.getTitle(),
                videoJpaEntity.getDescription(),
                videoJpaEntity.getUser().getUsername(),
                videoJpaEntity.getVideoFileName(),
                videoJpaEntity.getThumbnailFileName(),
                likes.get(),
                dislikes.get()
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
