package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

class EditVideoServiceTests {

    @Mock
    private StoreVideoPort storeVideoPort;

    @InjectMocks
    private EditVideoService editVideoService;

    @Captor
    private ArgumentCaptor<Video> videoArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editVideo_ShouldCallStoreVideoPortWithCorrectVideo() {
        EditVideoCommand command = new EditVideoCommand(
                "123",
                "New Title",
                "New Description"
        );

        editVideoService.editVideo(command);


        verify(storeVideoPort).editVideo(videoArgumentCaptor.capture());
        Video capturedVideo = videoArgumentCaptor.getValue();

        assertNotNull(capturedVideo);
        assertEquals("123", capturedVideo.getId());
        assertEquals("New Title", capturedVideo.getTitle());
        assertEquals("New Description", capturedVideo.getDescription());
    }
}
