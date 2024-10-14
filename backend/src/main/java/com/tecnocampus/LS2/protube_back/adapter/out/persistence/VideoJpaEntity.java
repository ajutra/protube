package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "videos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
class VideoJpaEntity {
    // Video data to be defined
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "title")
    private String title;
}
