package com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_video_like")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVideoLikeJpaEntity {
    @Id
    private String userVideoId = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserJpaEntity user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoJpaEntity video;

    @Column(name= "has_liked", nullable = false)
    private boolean hasLiked;

    @Column(name= "has_disliked", nullable = false)
    private boolean hasDisliked;

    public static UserVideoLikeJpaEntity from(
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
