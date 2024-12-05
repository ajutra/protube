package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagJpaEntity, String> {
}
