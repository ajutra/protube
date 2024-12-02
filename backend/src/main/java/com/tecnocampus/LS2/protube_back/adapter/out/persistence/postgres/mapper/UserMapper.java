package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    public User toDomain(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getUsername()
        );
    }

    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
                user.username(),
                Set.of()
        );
    }
}
