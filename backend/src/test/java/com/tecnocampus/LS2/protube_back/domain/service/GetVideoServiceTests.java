package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Field;
import com.tecnocampus.LS2.protube_back.domain.model.PlayerPageVideo;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.SearchVideoResultCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.SearchVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class GetVideoServiceTests {
    @Mock
    private GetVideoPort getVideoPort;

    @Mock
    private SearchVideoPort searchVideoPort;

    @InjectMocks
    private GetVideoService getVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVideoByTitleAndUsername_returnsVideoWhenExists() {
        Video expectedVideo = TestObjectFactory.createDummyVideo("1");

        when(getVideoPort.getVideoByTitleAndUsername(expectedVideo.getTitle(), expectedVideo.getUsername()))
                .thenReturn(expectedVideo);

        Video result = getVideoService.getVideoByTitleAndUsername(expectedVideo.getTitle(), expectedVideo.getUsername());

        assertEquals(expectedVideo, result);
    }

    @Test
    void getVideoByTitleAndUsername_throwsExceptionWhenNotExists() {
        when(getVideoPort.getVideoByTitleAndUsername(any(), any())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> getVideoService.getVideoByTitleAndUsername("1", "1"));
    }

    @Test
    void getVideoByIdReturnsVideo() {
        String videoId = "1";
        PlayerPageVideo playerPageVideo = TestObjectFactory.createDummyPlayerPageVideo(videoId);
        when(getVideoPort.getVideoWithFieldsById(videoId, Set.of(Field.CATEGORIES, Field.TAGS, Field.COMMENTS)))
                .thenReturn(playerPageVideo);

        GetVideoCommand result = getVideoService.getVideoById(videoId);

        assertEquals(GetVideoCommand.from(playerPageVideo.video(), playerPageVideo.categories(), playerPageVideo.tags(), playerPageVideo.comments()), result);
    }

    @Test
    void getVideoByIdThrowsExceptionWhenVideoNotFound() {
        String videoId = "999";
        when(getVideoPort.getVideoWithFieldsById(videoId, Set.of(Field.CATEGORIES, Field.TAGS, Field.COMMENTS)))
                .thenThrow(new NoSuchElementException("Video not found"));

        assertThrows(NoSuchElementException.class, () -> getVideoService.getVideoById(videoId));
    }

    @Test
    void searchVideosReturnsOkResult() {
        String searchText = "Test";
        Video video = TestObjectFactory.createDummyVideo("1");
        SearchVideoResultCommand searchVideoResultCommand = SearchVideoResultCommand.from(video);

        when(searchVideoPort.searchVideos(searchText)).thenReturn(List.of(video));

        List<SearchVideoResultCommand> result = getVideoService.searchVideos(searchText);

        assertEquals(List.of(searchVideoResultCommand), result);
    }

    @Test
    void searchVideosReturnsEmptyListWhenNoMatches() {
        String searchText = "NonExistent";
        when(searchVideoPort.searchVideos(searchText)).thenReturn(List.of());

        List<SearchVideoResultCommand> result = getVideoService.searchVideos(searchText);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchVideosHandlesException() {
        String searchText = "ErrorSearch";
        when(searchVideoPort.searchVideos(searchText)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> getVideoService.searchVideos(searchText));
    }
}
