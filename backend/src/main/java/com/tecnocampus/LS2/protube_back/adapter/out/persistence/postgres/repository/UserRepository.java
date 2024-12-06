package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, String> {
}
