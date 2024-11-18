package com.tecnocampus.LS2.protube_back.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

class EditCommentServiceTests {

    @Mock
    private StoreCommentPort storeCommentPort;

    @InjectMocks
    private EditCommentService editCommentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editCommentSuccessfully() {
        EditCommentCommand command = TestObjectFactory.createDummyEditCommentCommand("1");
        Comment comment = Comment.from(command);

        doNothing().when(storeCommentPort).editComment(comment);

        editCommentService.editComment(command);

        verify(storeCommentPort, times(1)).editComment(comment);
    }

    @Test
    void editCommentThrowsExceptionWhenCommentNotFound() {
        EditCommentCommand command = TestObjectFactory.createDummyEditCommentCommand("1");
        Comment comment = Comment.from(command);

        doThrow(new NoSuchElementException("Comment not found")).when(storeCommentPort).editComment(comment);

        assertThrows(NoSuchElementException.class, () -> editCommentService.editComment(command));

        verify(storeCommentPort, times(1)).editComment(comment);
    }
}