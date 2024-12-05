package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {
    Optional<VideoJpaEntity> findByTitleAndUserUsername(String title, String username);
    List<VideoJpaEntity> findAllByUserUsername(String username);
}
