package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "\"user\"") // This is a reserved word in PostgreSQL, so it needs to be escaped
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJpaEntity {
    @Id
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserVideoLikeJpaEntity> userVideoLikes;
}
