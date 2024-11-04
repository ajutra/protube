package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.*;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllVideosServiceTests {

    @Mock
    private GetVideoPort getVideoPort;

    @InjectMocks
    private GetAllVideosService getVideosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        Video video1 = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(
                TestObjectFactory.createDummyTag("1"),
                TestObjectFactory.createDummyTag("2"));

        List<Category> categories = List.of(
                TestObjectFactory.createDummyCategory("1"),
                TestObjectFactory.createDummyCategory("2"));

        List<Comment> comments = List.of(
                TestObjectFactory.createDummyComment("1"),
                TestObjectFactory.createDummyComment("2"));

        List<GetVideoCommand> videoCommands = List.of(
                GetVideoCommand.from(
                        video1,
                        categories,
                        tags,
                        comments));

        List<PlayerPageVideo> playerPageVideoList = List.of(
                PlayerPageVideo.from(video1, tags, categories, comments));

        when(getVideoPort.getAllVideosWithTagsCategoriesAndComments()).thenReturn(playerPageVideoList);

        List<GetVideoCommand> result = getVideosService.getAllVideos();

        assertEquals(1, result.size());
        assertEquals(videoCommands.getFirst().videoId(), result.getFirst().videoId());
        assertEquals(videoCommands.getFirst().width(), result.getFirst().width());
        assertEquals(videoCommands.getFirst().height(), result.getFirst().height());
        assertEquals(videoCommands.getFirst().duration(), result.getFirst().duration());
        assertEquals(videoCommands.getFirst().title(), result.getFirst().title());
        assertEquals(videoCommands.getFirst().username(), result.getFirst().username());
        assertEquals(videoCommands.getFirst().videoFileName(), result.getFirst().videoFileName());
        assertEquals(videoCommands.getFirst().thumbnailFileName(), result.getFirst().thumbnailFileName());
        assertEquals(videoCommands.getFirst().meta().description(), result.getFirst().meta().description());
        assertEquals(videoCommands.getFirst().meta().categories().size(), result.getFirst().meta().categories().size());
        assertEquals(videoCommands.getFirst().meta().tags().size(), result.getFirst().meta().tags().size());
        assertEquals(videoCommands.getFirst().meta().comments().size(), result.getFirst().meta().comments().size());
        assertEquals(videoCommands.getFirst().meta().categories().getFirst(), result.getFirst().meta().categories().getFirst());
        assertEquals(videoCommands.getFirst().meta().categories().getLast(), result.getFirst().meta().categories().getLast());
        assertEquals(videoCommands.getFirst().meta().tags().getFirst(), result.getFirst().meta().tags().getFirst());
        assertEquals(videoCommands.getFirst().meta().tags().getLast(), result.getFirst().meta().tags().getLast());
        assertEquals(videoCommands.getFirst().meta().comments().getFirst(), result.getFirst().meta().comments().getFirst());
        assertEquals(videoCommands.getFirst().meta().comments().getLast(), result.getFirst().meta().comments().getLast());

        verify(getVideoPort).getAllVideosWithTagsCategoriesAndComments();
    }

    @Test
    void getAllVideosHandlesEmptyTagsCategoriesAndComments() {
        Video video1 = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of();
        List<Category> categories = List.of();
        List<Comment> comments = List.of();

        List<GetVideoCommand> videoCommands = List.of(
                GetVideoCommand.from(
                        video1,
                        categories,
                        tags,
                        comments));

        List<PlayerPageVideo> playerPageVideoList = List.of(
                PlayerPageVideo.from(video1, tags, categories, comments));

        when(getVideoPort.getAllVideosWithTagsCategoriesAndComments()).thenReturn(playerPageVideoList);

        List<GetVideoCommand> result = getVideosService.getAllVideos();

        assertEquals(1, result.size());
        assertEquals(videoCommands.getFirst().videoId(), result.getFirst().videoId());
        assertEquals(videoCommands.getFirst().width(), result.getFirst().width());
        assertEquals(videoCommands.getFirst().height(), result.getFirst().height());
        assertEquals(videoCommands.getFirst().duration(), result.getFirst().duration());
        assertEquals(videoCommands.getFirst().title(), result.getFirst().title());
        assertEquals(videoCommands.getFirst().username(), result.getFirst().username());
        assertEquals(videoCommands.getFirst().videoFileName(), result.getFirst().videoFileName());
        assertEquals(videoCommands.getFirst().thumbnailFileName(), result.getFirst().thumbnailFileName());
        assertEquals(videoCommands.getFirst().meta().description(), result.getFirst().meta().description());
        assertEquals(videoCommands.getFirst().meta().categories().isEmpty(), result.getFirst().meta().categories().isEmpty());
        assertEquals(videoCommands.getFirst().meta().tags().isEmpty(), result.getFirst().meta().tags().isEmpty());
        assertEquals(videoCommands.getFirst().meta().comments().isEmpty(), result.getFirst().meta().comments().isEmpty());

        verify(getVideoPort).getAllVideosWithTagsCategoriesAndComments();
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(getVideoPort.getAllVideos()).thenReturn(List.of());

        List<GetVideoCommand> result = getVideosService.getAllVideos();

        assertTrue(result.isEmpty());
    }
}
