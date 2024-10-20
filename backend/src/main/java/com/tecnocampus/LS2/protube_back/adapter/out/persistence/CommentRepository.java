package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface CommentRepository extends JpaRepository<CommentJpaEntity, String> {
}
