package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CommentMapper;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void getAllCommentsByVideo_returnsListOfComments() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        CommentJpaEntity commentJpaEntity1 = TestObjectFactory.createDummyCommentJpaEntity("1");
        CommentJpaEntity commentJpaEntity2 = TestObjectFactory.createDummyCommentJpaEntity("2");
        Comment comment1 = TestObjectFactory.createDummyComment("1");
        Comment comment2 = TestObjectFactory.createDummyComment("2");

        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of(commentJpaEntity1, commentJpaEntity2));
        when(commentMapper.toDomain(commentJpaEntity1)).thenReturn(comment1);
        when(commentMapper.toDomain(commentJpaEntity2)).thenReturn(comment2);

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity);

        assertEquals(2, result.size());
        assertEquals(comment1, result.getFirst());
        assertEquals(comment2, result.getLast());
    }

    @Test
    void getAllCommentsByVideo_returnsEmptyListWhenNoComments() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of());

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity);

        assertTrue(result.isEmpty());
    }
}
