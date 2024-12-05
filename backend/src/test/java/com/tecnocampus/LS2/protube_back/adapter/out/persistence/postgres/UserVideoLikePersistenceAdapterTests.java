package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserVideoLikeJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.UserVideoLikeMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.UserVideoLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserVideoLikePersistenceAdapterTests {

    @Mock
    private UserVideoLikeRepository userVideoLikeRepository;

    @Mock
    private VideoPersistenceAdapter videoPersistenceAdapter;

    @Mock
    private UserPersistenceAdapter userPersistenceAdapter;

    @Mock
    private UserVideoLikeMapper userVideoLikeMapper;

    @InjectMocks
    private UserVideoLikePersistenceAdapter userVideoLikePersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likeVideoUpdatesExistingLike() {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = new UserVideoLikeJpaEntity();
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(userVideoLikeJpaEntity);

        userVideoLikePersistenceAdapter.likeVideo("user1", "video1");

        assertTrue(userVideoLikeJpaEntity.isHasLiked());
        assertFalse(userVideoLikeJpaEntity.isHasDisliked());
        verify(userVideoLikeRepository).save(userVideoLikeJpaEntity);
    }

    @Test
    void likeVideoCreatesNewLike() {
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(null);
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity();
        when(userPersistenceAdapter.findByUsername(anyString())).thenReturn(userJpaEntity);
        when(videoPersistenceAdapter.findById(anyString())).thenReturn(videoJpaEntity);
        UserVideoLikeJpaEntity newLike = new UserVideoLikeJpaEntity();
        when(userVideoLikeMapper.UserVideoLikeFrom(any(), any(), anyBoolean(), anyBoolean())).thenReturn(newLike);

        userVideoLikePersistenceAdapter.likeVideo("user1", "video1");

        verify(userVideoLikeRepository).save(newLike);
    }

    @Test
    void dislikeVideoUpdatesExistingDislike() {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = new UserVideoLikeJpaEntity();
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(userVideoLikeJpaEntity);

        userVideoLikePersistenceAdapter.dislikeVideo("user1", "video1");

        assertFalse(userVideoLikeJpaEntity.isHasLiked());
        assertTrue(userVideoLikeJpaEntity.isHasDisliked());
        verify(userVideoLikeRepository).save(userVideoLikeJpaEntity);
    }

    @Test
    void dislikeVideoCreatesNewDislike() {
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(null);
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity();
        when(userPersistenceAdapter.findByUsername(anyString())).thenReturn(userJpaEntity);
        when(videoPersistenceAdapter.findById(anyString())).thenReturn(videoJpaEntity);
        UserVideoLikeJpaEntity newDislike = new UserVideoLikeJpaEntity();
        when(userVideoLikeMapper.UserVideoLikeFrom(any(), any(), anyBoolean(), anyBoolean())).thenReturn(newDislike);

        userVideoLikePersistenceAdapter.dislikeVideo("user1", "video1");

        verify(userVideoLikeRepository).save(newDislike);
    }

    @Test
    void removeLikeOrDislikeDeletesExistingLikeOrDislike() {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = new UserVideoLikeJpaEntity();
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(userVideoLikeJpaEntity);

        userVideoLikePersistenceAdapter.removeLikeOrDislike("user1", "video1");

        verify(userVideoLikeRepository).delete(userVideoLikeJpaEntity);
    }

    @Test
    void getLikesAndDislikesReturnsCorrectValues() {
        UserVideoLikeJpaEntity userVideoLikeJpaEntity = new UserVideoLikeJpaEntity();
        userVideoLikeJpaEntity.setHasLiked(true);
        userVideoLikeJpaEntity.setHasDisliked(false);
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(userVideoLikeJpaEntity);

        Map<String, Boolean> result = userVideoLikePersistenceAdapter.getLikesAndDislikes("user1", "video1");

        assertEquals(true, result.get("hasLiked"));
        assertEquals(false, result.get("hasDisliked"));
    }

    @Test
    void getLikesAndDislikesReturnsFalseForNonExistingLikeOrDislike() {
        when(userVideoLikeRepository.findUserVideoLikeJpaEntityByUser_UsernameAndVideo_VideoId(anyString(), anyString()))
                .thenReturn(null);

        Map<String, Boolean> result = userVideoLikePersistenceAdapter.getLikesAndDislikes("user1", "video1");

        assertEquals(false, result.get("hasLiked"));
        assertEquals(false, result.get("hasDisliked"));
    }
}