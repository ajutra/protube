package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserVideoLikeJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserVideoLikeMapper {
    public UserVideoLikeJpaEntity UserVideoLikeFrom(
            UserJpaEntity userJpaEntity,
            VideoJpaEntity videoJpaEntity,
            boolean hasLiked,
            boolean hasDisliked) {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = new UserVideoLikeJpaEntity();
        userVideoLikeJpaEntity.setUser(userJpaEntity);
        userVideoLikeJpaEntity.setVideo(videoJpaEntity);
        userVideoLikeJpaEntity.setHasLiked(hasLiked);
        userVideoLikeJpaEntity.setHasDisliked(hasDisliked);

        return userVideoLikeJpaEntity;
    }
}
