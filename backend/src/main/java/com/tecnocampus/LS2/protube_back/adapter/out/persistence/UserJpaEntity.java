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
@Table(name = "\"user\"") // This is a reserved word in PostgreSQL, so it needs to be escaped
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJpaEntity {
    @Id
    private String user_id = UUID.randomUUID().toString();

    @Column(name = "username", nullable = false)
    private String username;
}
