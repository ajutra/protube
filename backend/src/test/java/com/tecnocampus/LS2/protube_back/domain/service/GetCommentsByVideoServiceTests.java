package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GetCommentsByVideoServiceTests {

    @Mock
    private GetCommentPort getCommentPort;

    @Mock
    private GetVideoService getVideoService;

    @InjectMocks
    private GetCommentsByVideoService getCommentsByVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCommentsByVideo() {
        String videoId = "1";
        Video video = TestObjectFactory.createDummyVideo(videoId);
        List<Comment> comments = List.of(TestObjectFactory.createDummyComment("1"));

        when(getVideoService.getVideoById(videoId)).thenReturn(video);
        when(getCommentPort.getAllCommentsByVideo(video)).thenReturn(comments);

        List<GetCommentCommand> result = getCommentsByVideoService.getAllCommentsByVideo(videoId);

        List<GetCommentCommand> expectedComments = comments.stream()
                .map(GetCommentCommand::from)
                .toList();

        assertEquals(expectedComments, result);
        verify(getVideoService).getVideoById(videoId);
        verify(getCommentPort).getAllCommentsByVideo(video);
    }

    @Test
    void getAllCommentsByVideoReturnsListOfComments() {
        String videoId = "1";
        Video video = TestObjectFactory.createDummyVideo(videoId);
        List<Comment> comments = List.of(TestObjectFactory.createDummyComment("1"));
        List<GetCommentCommand> commentCommands = comments.stream()
                .map(GetCommentCommand::from)
                .toList();

        when(getVideoService.getVideoById(videoId)).thenReturn(video);
        when(getCommentPort.getAllCommentsByVideo(video)).thenReturn(comments);

        List<GetCommentCommand> result = getCommentsByVideoService.getAllCommentsByVideo(videoId);

        assertEquals(1, result.size());
        assertEquals(commentCommands.get(0).text(), result.get(0).text());

        verify(getVideoService).getVideoById(videoId);
        verify(getCommentPort).getAllCommentsByVideo(video);
    }

    @Test
    void getAllCommentsByVideoReturnsEmptyListWhenNoComments() {
        String videoId = "1";
        Video video = TestObjectFactory.createDummyVideo(videoId);

        when(getVideoService.getVideoById(videoId)).thenReturn(video);
        when(getCommentPort.getAllCommentsByVideo(video)).thenReturn(List.of());

        List<GetCommentCommand> result = getCommentsByVideoService.getAllCommentsByVideo(videoId);

        assertTrue(result.isEmpty());
        verify(getVideoService).getVideoById(videoId);
        verify(getCommentPort).getAllCommentsByVideo(video); }
}
