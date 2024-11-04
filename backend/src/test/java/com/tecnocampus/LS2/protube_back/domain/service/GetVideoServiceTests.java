package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class GetVideoServiceTests {
    @Mock
    private GetVideoPort getVideoPort;

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
    void getVideoById_returnsVideoWhenExists() {
        Video expectedVideo = TestObjectFactory.createDummyVideo("1");

        when(getVideoPort.getVideoById(expectedVideo.getId())).thenReturn(expectedVideo);

        Video result = getVideoService.getVideoById(expectedVideo.getId());

        assertEquals(expectedVideo, result);
    }

    @Test
    void getVideoById_throwsExceptionWhenNotExists() {
        when(getVideoPort.getVideoById(any())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> getVideoService.getVideoById("invalid id"));
    }
}
