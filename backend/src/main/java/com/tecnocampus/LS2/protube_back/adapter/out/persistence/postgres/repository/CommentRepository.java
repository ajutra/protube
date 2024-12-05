package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, String> {
    List<CommentJpaEntity> findAllByVideoOrderByCommentIdAsc(VideoJpaEntity video);

    List<CommentJpaEntity> findByUserUsernameOrderByCommentIdAsc(String username);

    void deleteAllByVideo(VideoJpaEntity video);
}
