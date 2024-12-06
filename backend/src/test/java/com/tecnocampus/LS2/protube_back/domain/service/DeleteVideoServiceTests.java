package com.tecnocampus.LS2.protube_back.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DeleteVideoServiceTests {

    @Mock
    private DeleteVideoPort deleteVideoPort;

    @Mock
    private GetVideoService getVideoService;

    @InjectMocks
    private DeleteVideoService deleteVideoService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Field storageDirField = DeleteVideoService.class.getDeclaredField("storageDir");
        storageDirField.setAccessible(true);
        storageDirField.set(deleteVideoService, "test-storage-dir");
    }

    @Test
    void deleteVideoSuccessfully() throws IOException {
        String videoId = "1";
        GetVideoCommand getVideoCommand = TestObjectFactory.createDummyGetVideoCommand("1");
        Path storageDirPath = Path.of("test-storage-dir");
        Path videoPath = Paths.get(storageDirPath.toString(), getVideoCommand.videoFileName());
        Path thumbnailPath = Paths.get(storageDirPath.toString(), getVideoCommand.thumbnailFileName());
        Files.createDirectory(storageDirPath);
        Files.createFile(videoPath);
        Files.createFile(thumbnailPath);

        when(getVideoService.getVideoById(videoId)).thenReturn(getVideoCommand);

        doNothing().when(deleteVideoPort).deleteVideo(videoId);

        deleteVideoService.deleteVideo(videoId);

        verify(getVideoService, times(1)).getVideoById(videoId);
        verify(deleteVideoPort, times(2)).deleteVideo(videoId);
        Files.delete(storageDirPath);
    }

    @Test
    void deleteVideoThrowsRuntimeException() throws IOException {
        String videoId = "1";
        GetVideoCommand getVideoCommand = TestObjectFactory.createDummyGetVideoCommand("1");
        Path storageDirPath = Path.of("test-storage-dir");
        Files.createDirectory(storageDirPath);

        when(getVideoService.getVideoById(videoId)).thenReturn(getVideoCommand);

        assertThrows(RuntimeException.class, () -> deleteVideoService.deleteVideo(videoId));

        verify(getVideoService, times(1)).getVideoById(videoId);
        verify(deleteVideoPort, never()).deleteVideo(videoId);
        Files.delete(storageDirPath);
    }
}