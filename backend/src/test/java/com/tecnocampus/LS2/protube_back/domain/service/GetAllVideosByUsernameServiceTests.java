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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllVideosByUsernameServiceTests {

    @Mock
    private GetVideoPort getVideoPort;

    @InjectMocks
    private GetAllVideosByUsernameService getAllVideosByUsernameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosByUsernameReturnsListOfVideos() {
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

        when(getVideoPort.getAllVideosWithFieldsByUsername(anyString(), any())).thenReturn(playerPageVideoList);

        List<GetVideoCommand> result = getAllVideosByUsernameService.getAllVideosByUsername("username");

        assertEquals(1, result.size());
        assertEquals(videoCommands.getFirst(), result.getFirst());

        verify(getVideoPort).getAllVideosWithFieldsByUsername(anyString(), any());
    }

    @Test
    void getAllVideosByUsernameHandlesEmptyTagsCategoriesAndComments() {
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

        when(getVideoPort.getAllVideosWithFieldsByUsername(anyString(), any())).thenReturn(playerPageVideoList);

        List<GetVideoCommand> result = getAllVideosByUsernameService.getAllVideosByUsername("username");

        assertEquals(1, result.size());
        assertEquals(videoCommands.getFirst(), result.getFirst());

        verify(getVideoPort).getAllVideosWithFieldsByUsername(anyString(), any());
    }

    @Test
    void getAllVideosByUsernameReturnsEmptyListWhenNoVideos() {
        when(getVideoPort.getAllVideosWithFieldsByUsername(anyString(), any())).thenReturn(List.of());

        List<GetVideoCommand> result = getAllVideosByUsernameService.getAllVideosByUsername("username");

        assertTrue(result.isEmpty());
    }
}