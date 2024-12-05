package com.tecnocampus.LS2.protube_back.domain.service;

import static org.mockito.Mockito.*;

import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DeleteVideoServiceTests {

    @Mock
    private DeleteVideoPort deleteVideoPort;

    @InjectMocks
    private DeleteVideoService deleteVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteVideoSuccessfully() {
        String videoId = "1";

        doNothing().when(deleteVideoPort).deleteVideo(videoId);

        deleteVideoService.deleteVideo(videoId);

        verify(deleteVideoPort, times(2)).deleteVideo(videoId);
    }
}