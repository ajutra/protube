package com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentJpaEntity, String> {
    List<CommentJpaEntity> findAllByVideo(VideoJpaEntity video);
}
