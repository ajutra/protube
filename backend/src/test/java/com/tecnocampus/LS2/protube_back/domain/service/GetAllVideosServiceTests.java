package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllVideosServiceTests {

    @Mock
    private GetVideosPort getVideosPort;

    @InjectMocks
    private GetAllVideosService getVideosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        Video video1 = TestObjectFactory.createDummyVideo("1");
        List<Video> videos = List.of(video1);
        List<GetVideoCommand> videoCommands =
                videos.stream()
                        .map(video -> GetVideoCommand.from(video,
                                List.of(),
                                List.of(),
                                List.of()))
                        .collect(Collectors.toList());

        when(getVideosPort.getAllVideos()).thenReturn(videos);

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
        assertEquals(videoCommands.getFirst().meta().categories(), result.getFirst().meta().categories());
        assertEquals(videoCommands.getFirst().meta().tags(), result.getFirst().meta().tags());
        assertEquals(videoCommands.getFirst().meta().comments(), result.getFirst().meta().comments());
        assertEquals(videoCommands.getFirst(), result.getFirst());
        verify(getVideosPort).getAllVideos();
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(getVideosPort.getAllVideos()).thenReturn(List.of());

        List<GetVideoCommand> result = getVideosService.getAllVideos();

        assertTrue(result.isEmpty());
    }
}
