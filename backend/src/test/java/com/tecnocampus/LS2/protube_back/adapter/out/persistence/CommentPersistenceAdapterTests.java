package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CommentMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.CommentRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentPersistenceAdapterTests {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private VideoMapper videoMapper;


    @InjectMocks
    private CommentPersistenceAdapter commentPersistenceAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeCommentStoresNewComment() {
        Comment comment = TestObjectFactory.createDummyComment("1");
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("1");

        when(videoRepository.findById(comment.getVideoId())).thenReturn(Optional.of(videoJpaEntity));
        when(userRepository.findById(comment.getUsername())).thenReturn(Optional.of(userJpaEntity));
        when(commentMapper.toJpaEntity(comment, userJpaEntity, videoJpaEntity)).thenReturn(commentJpaEntity);

        commentPersistenceAdapter.storeComment(comment);

        verify(commentRepository, times(1)).save(commentJpaEntity);
    }

    @Test
    void getAllCommentsByVideoIdReturnsListOfComments() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        Comment expectedComment = TestObjectFactory.createDummyComment("1");
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");

        when(videoRepository.findById(anyString())).thenReturn(Optional.of(videoJpaEntity));
        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of(commentJpaEntity));
        when(commentMapper.toDomain(commentJpaEntity)).thenReturn(expectedComment);

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideoId("test id");

        assertEquals(List.of(expectedComment), result);

        verify(commentRepository).findAllByVideo(videoJpaEntity);
        verify(commentMapper).toDomain(commentJpaEntity);
    }

    @Test
    void getAllCommentsByVideoIdReturnsEmptyListWhenNoComments() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        when(videoRepository.findById(anyString())).thenReturn(Optional.of(videoJpaEntity));
        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of());

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideoId("test id");

        assertTrue(result.isEmpty());

        verify(commentRepository).findAllByVideo(videoJpaEntity);
    }

    @Test
    void getAllCommentsByVideoIdThrowsExceptionWhenNoVideoFound() {
        when(videoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentPersistenceAdapter.getAllCommentsByVideoId("test id"));
    }
}
