package com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "video")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoJpaEntity {
    @Id
    private String videoId = UUID.randomUUID().toString();

    @Column(name = "width", nullable = false)
    private int width;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "video_file_name", nullable = false)
    private String videoFileName;

    @Column(name = "thumbnail_file_name", nullable = false)
    private String thumbnailFileName;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserJpaEntity user;

    @ManyToMany
    @JoinTable(
            name = "video_tag",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private Set<TagJpaEntity> tags;

    @ManyToMany
    @JoinTable(
            name = "video_category",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name")
    )
    private Set<CategoryJpaEntity> categories;
}
