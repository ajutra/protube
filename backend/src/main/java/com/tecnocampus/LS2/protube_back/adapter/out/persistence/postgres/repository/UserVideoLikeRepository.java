package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserVideoLikeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVideoLikeRepository extends JpaRepository<UserVideoLikeJpaEntity, String> {
    UserVideoLikeJpaEntity findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(String username, String videoId);
}
