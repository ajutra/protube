package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.UserPersistenceAdapter;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.VideoPersistenceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

class StoreCommentServiceTests {

    @InjectMocks
    private StoreCommentService storeCommentService;

    @Mock
    private StoreCommentPort storeCommentPort;

    @Mock
    private UserPersistenceAdapter userPersistenceAdapter;

    @Mock
    private VideoPersistenceAdapter videoPersistenceAdapter;

    @Mock
    private StoreUserService storeUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeCommentWhenUserAndVideoExist() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("1");

        doNothing().when(userPersistenceAdapter).checkIfUserExists(command.username());
        doNothing().when(videoPersistenceAdapter).checkIfVideoExists(command.videoId());

        storeCommentService.storeComment(command);

        verify(storeCommentPort, times(1)).storeComment(any(Comment.class));
    }

    @Test
    void storeCommentWhenUserDoesNotExist() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("2");

        doThrow(new NoSuchElementException("User not found")).when(userPersistenceAdapter).checkIfUserExists(command.username());

        assertThrows(NoSuchElementException.class, () -> storeCommentService.storeComment(command));
    }

    @Test
    void storeCommentWhenVideoDoesNotExist() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("3");

        doNothing().when(userPersistenceAdapter).checkIfUserExists(command.username());
        doThrow(new NoSuchElementException("Video not found")).when(videoPersistenceAdapter).checkIfVideoExists(command.videoId());

        assertThrows(NoSuchElementException.class, () -> storeCommentService.storeComment(command));
    }

    @Test
    void storeCommentFromStoreVideoServiceWhenUserDoesNotExist() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("4");
        Video video = TestObjectFactory.createDummyVideo("video1");

        doThrow(new IllegalArgumentException("User already exists")).when(storeUserService).storeUser(any());

        storeCommentService.storeCommentFromStoreVideoService(command, video);

        verify(storeCommentPort, times(1)).storeComment(any(Comment.class));
    }

    @Test
    void storeCommentFromStoreVideoServiceWhenUserExists() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("5");
        Video video = TestObjectFactory.createDummyVideo("video2");

        doNothing().when(storeUserService).storeUser(any());

        storeCommentService.storeCommentFromStoreVideoService(command, video);

        verify(storeCommentPort, times(1)).storeComment(any(Comment.class));
    }

    @Test
    void storeCommentFromStoreVideoServiceSetsCorrectVideoId() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("7");
        Video video = TestObjectFactory.createDummyVideo("video3");

        doNothing().when(storeUserService).storeUser(any());

        storeCommentService.storeCommentFromStoreVideoService(command, video);

        ArgumentCaptor<Comment> commentCaptor = forClass(Comment.class);
        verify(storeCommentPort, times(1)).storeComment(commentCaptor.capture());

        Comment capturedComment = commentCaptor.getValue();
        assertEquals(video.getId(), capturedComment.getVideoId());
    }
}
