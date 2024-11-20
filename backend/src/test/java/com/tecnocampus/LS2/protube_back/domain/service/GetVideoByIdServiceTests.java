package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Field;
import com.tecnocampus.LS2.protube_back.domain.model.PlayerPageVideo;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetVideoByIdServiceTests {

    @Mock
    private GetVideoPort getVideoPort;

    @InjectMocks
    private GetVideoByIdService getVideoByIdService;

    @Test
    void getVideoByIdReturnsVideo() {
        String videoId = "1";
        PlayerPageVideo playerPageVideo = TestObjectFactory.createDummyPlayerPageVideo(videoId);
        when(getVideoPort.getVideoWithFieldsById(videoId, Set.of(Field.CATEGORIES, Field.TAGS, Field.COMMENTS)))
                .thenReturn(playerPageVideo);

        GetVideoCommand result = getVideoByIdService.getVideoById(videoId);

        assertEquals(GetVideoCommand.from(playerPageVideo.video(), playerPageVideo.categories(), playerPageVideo.tags(), playerPageVideo.comments()), result);
    }

    @Test
    void getVideoByIdThrowsExceptionWhenVideoNotFound() {
        String videoId = "999";
        when(getVideoPort.getVideoWithFieldsById(videoId, Set.of(Field.CATEGORIES, Field.TAGS, Field.COMMENTS)))
                .thenThrow(new NoSuchElementException("Video not found"));

        assertThrows(NoSuchElementException.class, () -> getVideoByIdService.getVideoById(videoId));
    }
}