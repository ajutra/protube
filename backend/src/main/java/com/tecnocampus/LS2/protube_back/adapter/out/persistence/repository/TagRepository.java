package com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagJpaEntity, String> {
}
