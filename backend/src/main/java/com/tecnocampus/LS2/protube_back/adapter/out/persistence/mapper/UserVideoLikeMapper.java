package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserVideoLikeJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
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
