package com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentJpaEntity {
    @Id
    private String comment_id = UUID.randomUUID().toString();

    @Column(name = "text", nullable = false, length = 10000)
    private String text;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserJpaEntity user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoJpaEntity video;
}
