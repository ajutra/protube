package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.UserPersistenceAdapter;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.VideoPersistenceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeCommentWhenUserAndVideoExist() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "Great video!");

        doNothing().when(userPersistenceAdapter).checkIfUserExists(command.username());
        doNothing().when(videoPersistenceAdapter).checkIfVideoExists(command.videoId());

        storeCommentService.storeComment(command);

        verify(storeCommentPort, times(1)).storeComment(any(Comment.class));
    }

    @Test
    void storeCommentWhenUserDoesNotExist() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "Great video!");

        doThrow(new NoSuchElementException("User not found")).when(userPersistenceAdapter).checkIfUserExists(command.username());

        assertThrows(NoSuchElementException.class, () -> storeCommentService.storeComment(command));
    }

    @Test
    void storeCommentWhenVideoDoesNotExist() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "Great video!");

        doNothing().when(userPersistenceAdapter).checkIfUserExists(command.username());
        doThrow(new NoSuchElementException("Video not found")).when(videoPersistenceAdapter).checkIfVideoExists(command.videoId());

        assertThrows(NoSuchElementException.class, () -> storeCommentService.storeComment(command));
    }
}
