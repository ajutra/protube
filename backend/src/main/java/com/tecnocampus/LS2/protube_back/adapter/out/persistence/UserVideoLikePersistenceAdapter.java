package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserVideoLikeJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.UserVideoLikeRepository;
import com.tecnocampus.LS2.protube_back.port.out.UserVideoLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserVideoLikePersistenceAdapter implements UserVideoLikePort {
    private final UserVideoLikeRepository userVideoLikeRepository;
    private final VideoPersistenceAdapter videoPersistenceAdapter;
    private final UserPersistenceAdapter userPersistenceAdapter;

    @Override
    public void likeVideo(String username, String videoId) {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = userVideoLikeRepository
                .findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(username, videoId);

        if (userVideoLikeJpaEntity != null) {
            userVideoLikeJpaEntity.setHasLiked(true);
            userVideoLikeJpaEntity.setHasDisliked(false);
            userVideoLikeRepository.save(userVideoLikeJpaEntity);
        } else {
            UserJpaEntity userJpaEntity = userPersistenceAdapter.findByUsername(username);
            VideoJpaEntity videoJpaEntity = videoPersistenceAdapter.findById(videoId);
            userVideoLikeRepository.save(UserVideoLikeJpaEntity.from(userJpaEntity, videoJpaEntity, true, false));
        }
    }

    @Override
    public void dislikeVideo(String username, String videoId) {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = userVideoLikeRepository
                .findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(username, videoId);

        if (userVideoLikeJpaEntity != null) {
            userVideoLikeJpaEntity.setHasLiked(false);
            userVideoLikeJpaEntity.setHasDisliked(true);
            userVideoLikeRepository.save(userVideoLikeJpaEntity);
        } else {
            UserJpaEntity userJpaEntity = userPersistenceAdapter.findByUsername(username);
            VideoJpaEntity videoJpaEntity = videoPersistenceAdapter.findById(videoId);
            userVideoLikeRepository.save(UserVideoLikeJpaEntity.from(userJpaEntity, videoJpaEntity, false, true));
        }
    }

    @Override
    public void removeLikeOrDislike(String username, String videoId) {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = userVideoLikeRepository
                .findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(username, videoId);

        if (userVideoLikeJpaEntity != null) {
            userVideoLikeRepository.delete(userVideoLikeJpaEntity);
        }
    }

    @Override
    public Map<String, Boolean> getLikesAndDislikes(String username, String videoId) {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = userVideoLikeRepository
                .findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(username, videoId);

        if (userVideoLikeJpaEntity != null) {
            return Map.of("hasLiked", userVideoLikeJpaEntity.isHasLiked(), "hasDisliked", userVideoLikeJpaEntity.isHasDisliked());
        } else {
            return Map.of("hasLiked", false, "hasDisliked", false);
        }
    }
}
