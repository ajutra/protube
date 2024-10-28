package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StoreCommentControllerTests {

    @InjectMocks
    private StoreCommentController storeCommentController;

    @Mock
    private StoreCommentUseCase storeCommentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeComment_ShouldReturnCreatedStatus() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "Amazing content!");

        // No exception is thrown, so it should return 201
        doNothing().when(storeCommentUseCase).storeComment(command);

        ResponseEntity<Void> response = storeCommentController.storeComment(command);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void storeComment_WhenInvalidArgument_ShouldReturnBadRequestStatus() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "Great video!");

        // Simulate IllegalArgumentException
        doThrow(new IllegalArgumentException("Invalid comment data")).when(storeCommentUseCase).storeComment(command);

        ResponseEntity<Void> response = storeCommentController.storeComment(command);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
