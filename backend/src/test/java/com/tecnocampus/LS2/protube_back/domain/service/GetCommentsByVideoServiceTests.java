package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCommentsByVideoServiceTests {

    @Mock
    private GetCommentPort getCommentPort;

    @InjectMocks
    private GetCommentsByVideoService getCommentsByVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCommentsByVideoIdReturnsListOfComments() {
        Video video = TestObjectFactory.createDummyVideo("1");
        List<Comment> comments = List.of(TestObjectFactory.createDummyComment("1"));

        List<GetCommentCommand> expectedCommands = comments.stream()
                .map(GetCommentCommand::from)
                .toList();

        when(getCommentPort.getAllCommentsByVideoId(video.getId())).thenReturn(comments);

        List<GetCommentCommand> result = getCommentsByVideoService.getAllCommentsByVideoId(video.getId());

        assertEquals(expectedCommands, result);

        verify(getCommentPort).getAllCommentsByVideoId(video.getId());
    }

    @Test
    void getAllCommentsByVideoReturnsEmptyListWhenNoCommentsId() {
        Video video = TestObjectFactory.createDummyVideo("1");

        when(getCommentPort.getAllCommentsByVideoId(video.getId())).thenReturn(List.of());

        List<GetCommentCommand> result = getCommentsByVideoService.getAllCommentsByVideoId(video.getId());

        assertTrue(result.isEmpty());
        verify(getCommentPort).getAllCommentsByVideoId(video.getId());
    }

    @Test
    void getAllCommentsByVideoThrowsExceptionWhenNoVideoIdFound() {
        when(getCommentPort.getAllCommentsByVideoId(anyString())).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> getCommentsByVideoService.getAllCommentsByVideoId("invalid id"));
    }
}
