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
import com.tecnocampus.LS2.protube_back.domain.model.Video;
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
    void getAllCommentsByVideo_returnsCorrectComments() {
        // Arrange
        VideoJpaEntity video = TestObjectFactory.createDummyVideoJpaEntity("1");
        CommentJpaEntity commentJpaEntity1 = new CommentJpaEntity(null, "Comment Text 1", null, video);
        CommentJpaEntity commentJpaEntity2 = new CommentJpaEntity(null, "Comment Text 2", null, video);

        List<CommentJpaEntity> commentJpaEntities = List.of(commentJpaEntity1, commentJpaEntity2);

        when(commentRepository.findAllByVideo(video)).thenReturn(commentJpaEntities);

        when(commentMapper.toDomain(commentJpaEntity1)).thenReturn(new Comment("1", "VideoID-1", "User1", "Comment Text 1"));
        when(commentMapper.toDomain(commentJpaEntity2)).thenReturn(new Comment("2", "VideoID-2", "User2", "Comment Text 2"));

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideo(video);

        assertEquals(2, result.size());
        assertEquals("Comment Text 1", result.get(0).getText());
        assertEquals("Comment Text 2", result.get(1).getText());

        verify(commentRepository, times(1)).findAllByVideo(video);
        verify(commentMapper, times(1)).toDomain(commentJpaEntity1);
        verify(commentMapper, times(1)).toDomain(commentJpaEntity2);
    }
    @Test void getAllCommentsByVideoReturnsListOfComments() {
        Video video = TestObjectFactory.createDummyVideo("1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        Comment comment = TestObjectFactory.createDummyComment("1");
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");

        when(videoMapper.toJpaEntity(video)).thenReturn(videoJpaEntity);
        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of(commentJpaEntity));
        when(commentMapper.toDomain(commentJpaEntity)).thenReturn(comment);

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideo(video);

        assertEquals(1, result.size());
        assertEquals(comment, result.get(0));

        verify(videoMapper).toJpaEntity(video);
        verify(commentRepository).findAllByVideo(videoJpaEntity);
        verify(commentMapper).toDomain(commentJpaEntity);
    }

    @Test
    void getAllCommentsByVideoReturnsEmptyListWhenNoComments() {
        Video video = TestObjectFactory.createDummyVideo("1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        when(videoMapper.toJpaEntity(video)).thenReturn(videoJpaEntity);
        when(commentRepository.findAllByVideo(videoJpaEntity)).thenReturn(List.of());

        List<Comment> result = commentPersistenceAdapter.getAllCommentsByVideo(video);
        assertTrue(result.isEmpty());

        verify(videoMapper).toJpaEntity(video);
        verify(commentRepository).findAllByVideo(videoJpaEntity);
    }
}
