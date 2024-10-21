package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTests {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void mapUserJpaEntityToDomain() {
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("1");

        User user = userMapper.toDomain(userJpaEntity);

        assertEquals(userJpaEntity.getUsername(), user.username());
    }

    @Test
    void mapUserToJpaEntity() {
        User user = TestObjectFactory.createDummyUser("1");

        UserJpaEntity userJpaEntity = userMapper.toJpaEntity(user);

        assertEquals(user.username(), userJpaEntity.getUsername());
    }
}
