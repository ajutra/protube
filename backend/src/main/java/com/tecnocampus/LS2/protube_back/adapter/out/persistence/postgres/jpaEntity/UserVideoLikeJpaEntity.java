package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity;

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
}
