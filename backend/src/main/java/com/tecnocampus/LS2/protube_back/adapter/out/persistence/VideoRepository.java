package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {
}
