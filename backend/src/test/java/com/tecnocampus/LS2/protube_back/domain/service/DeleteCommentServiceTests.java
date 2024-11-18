package com.tecnocampus.LS2.protube_back.domain.service;

import static org.mockito.Mockito.*;

import com.tecnocampus.LS2.protube_back.port.out.DeleteCommentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DeleteCommentServiceTests {

    @Mock
    private DeleteCommentPort deleteCommentPort;

    @InjectMocks
    private DeleteCommentService deleteCommentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteCommentSuccessfully() {
        String commentId = "1";

        doNothing().when(deleteCommentPort).deleteComment(commentId);

        deleteCommentService.deleteComment(commentId);

        verify(deleteCommentPort, times(1)).deleteComment(commentId);
    }
}