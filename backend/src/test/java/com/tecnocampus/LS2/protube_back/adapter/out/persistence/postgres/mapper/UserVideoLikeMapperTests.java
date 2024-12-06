package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserVideoLikeJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.UserVideoLikeMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserVideoLikeMapperTests {
    private final UserVideoLikeMapper userVideoLikeMapper = new UserVideoLikeMapper();

    @Test
    void UserVideoLikeFromCreatesEntityWithCorrectValues() {
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("user1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("video1");

        UserVideoLikeJpaEntity result = userVideoLikeMapper.UserVideoLikeFrom(userJpaEntity, videoJpaEntity, true, false);

        assertNotNull(result);
        assertEquals(userJpaEntity, result.getUser());
        assertEquals(videoJpaEntity, result.getVideo());
        assertTrue(result.isHasLiked());
        assertFalse(result.isHasDisliked());
    }

    @Test
    void UserVideoLikeFromHandlesNullUser() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("video1");

        UserVideoLikeJpaEntity result = userVideoLikeMapper.UserVideoLikeFrom(null, videoJpaEntity, true, false);

        assertNotNull(result);
        assertNull(result.getUser());
        assertEquals(videoJpaEntity, result.getVideo());
        assertTrue(result.isHasLiked());
        assertFalse(result.isHasDisliked());
    }

    @Test
    void UserVideoLikeFromHandlesNullVideo() {
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("user1");

        UserVideoLikeJpaEntity result = userVideoLikeMapper.UserVideoLikeFrom(userJpaEntity, null, true, false);

        assertNotNull(result);
        assertEquals(userJpaEntity, result.getUser());
        assertNull(result.getVideo());
        assertTrue(result.isHasLiked());
        assertFalse(result.isHasDisliked());
    }

    @Test
    void UserVideoLikeFromHandlesAllNullValues() {
        UserVideoLikeJpaEntity result = userVideoLikeMapper.UserVideoLikeFrom(null, null, false, false);

        assertNotNull(result);
        assertNull(result.getUser());
        assertNull(result.getVideo());
        assertFalse(result.isHasLiked());
        assertFalse(result.isHasDisliked());
    }
}
