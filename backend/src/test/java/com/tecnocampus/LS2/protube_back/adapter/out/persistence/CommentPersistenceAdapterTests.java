package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CommentMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

        when(commentRepository.findById(comment.id())).thenReturn(Optional.empty());
        when(videoRepository.findById(comment.video_id())).thenReturn(Optional.of(videoJpaEntity));
        when(userRepository.findById(comment.username())).thenReturn(Optional.of(userJpaEntity));
        when(commentMapper.toJpaEntity(comment, userJpaEntity, videoJpaEntity)).thenReturn(commentJpaEntity);

        commentPersistenceAdapter.storeComment(comment);

        verify(commentRepository, times(1)).findById(comment.id());
        verify(videoRepository, times(1)).findById(comment.video_id());
        verify(userRepository, times(1)).findById(comment.username());
        verify(commentMapper, times(1)).toJpaEntity(comment, userJpaEntity, videoJpaEntity);
        verify(commentRepository, times(1)).save(commentJpaEntity);
    }

    @Test
    void storeCommentDoesNotStoreExistingComment() {
        Comment comment = TestObjectFactory.createDummyComment("1");
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");

        when(commentRepository.findById(comment.id())).thenReturn(Optional.of(commentJpaEntity));

        commentPersistenceAdapter.storeComment(comment);

        verify(commentRepository, times(1)).findById(comment.id());
        verify(videoRepository, never()).findById(comment.video_id());
        verify(userRepository, never()).findById(comment.username());
        verify(commentMapper, never()).toJpaEntity(comment, null, null);
        verify(commentRepository, never()).save(commentJpaEntity);
    }
}
