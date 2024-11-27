package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UploadVideoServiceTests {

    private UploadVideoService uploadVideoService;
    private StoreVideoPort storeVideoPort;
    private GetUserService getUserService;

    private String storageDir = "test-storage-dir";

    @BeforeEach
    void setUp() throws IOException {
        storeVideoPort = mock(StoreVideoPort.class);
        getUserService = mock(GetUserService.class);
        uploadVideoService = new UploadVideoService(storeVideoPort, getUserService);
        try {
            java.lang.reflect.Field storageDirField = UploadVideoService.class.getDeclaredField("storageDir");
            storageDirField.setAccessible(true);
            storageDirField.set(uploadVideoService, storageDir);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set storageDir", e);
        }
        Files.createDirectories(Paths.get(storageDir));
    }

    @Test
    void storeVideoWithFilesStoresFilesAndVideo() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = new StoreVideoCommand(
                1920, 1080, 120, "title", "description", "username",
                "video.mp4", "thumbnail.png", List.of(), List.of(), List.of()
        );

        User user = new User("username");
        when(getUserService.getUserByUsername("username")).thenReturn(user);

        uploadVideoService.storeVideoWithFiles(file, thumbnail, storeVideoCommand);

        verify(storeVideoPort, times(1)).storeVideo(any(), any(Set.class), any(Set.class));

        Path videoPath = Paths.get(storageDir, file.getOriginalFilename());
        Path thumbnailPath = Paths.get(storageDir, thumbnail.getOriginalFilename());

        assert Files.exists(videoPath);
        assert Files.exists(thumbnailPath);

        Files.deleteIfExists(videoPath);
        Files.deleteIfExists(thumbnailPath);
    }

    @Test
    void storeVideoWithFilesThrowsIOException() throws IOException {
        MockMultipartFile file = spy(new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = new StoreVideoCommand(
                1920, 1080, 120, "title", "description", "username",
                "video.mp4", "thumbnail.png", List.of(), List.of(), List.of()
        );

        User user = new User("username");
        when(getUserService.getUserByUsername("username")).thenReturn(user);

        doThrow(new IOException("Test IOException")).when(file).getInputStream();

        assertThrows(RuntimeException.class, () -> uploadVideoService.storeVideoWithFiles(file, thumbnail, storeVideoCommand));
    }

    @Test
    void storeVideoWithFilesThrowsUnexpectedException() throws IOException {
        MockMultipartFile file = spy(new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = new StoreVideoCommand(
                1920, 1080, 120, "title", "description", "username",
                "video.mp4", "thumbnail.png", List.of(), List.of(), List.of()
        );

        User user = new User("username");
        when(getUserService.getUserByUsername("username")).thenReturn(user);

        doThrow(new RuntimeException("Test Unexpected Exception")).when(file).getInputStream();

        assertThrows(RuntimeException.class, () -> uploadVideoService.storeVideoWithFiles(file, thumbnail, storeVideoCommand));
    }

    @Test
    void storeVideoStoresVideo() {
        StoreVideoCommand storeVideoCommand = new StoreVideoCommand(
                1920, 1080, 120, "title", "description", "username",
                "video.mp4", "thumbnail.png", List.of(), List.of(), List.of()
        );
        User user = new User("username");
        when(getUserService.getUserByUsername("username")).thenReturn(user);

        uploadVideoService.storeVideo(storeVideoCommand);

        verify(storeVideoPort, times(1)).storeVideo(any(), any(Set.class), any(Set.class));
    }

    @Test
    void storeVideoThrowsExceptionWhenUserNotFound() {
        StoreVideoCommand storeVideoCommand = new StoreVideoCommand(
                1920, 1080, 120, "title", "description", "username",
                "video.mp4", "thumbnail.png", List.of(), List.of(), List.of()
        );
        when(getUserService.getUserByUsername("username")).thenThrow(new NoSuchElementException("User not found"));

        assertThrows(NoSuchElementException.class, () -> uploadVideoService.storeVideo(storeVideoCommand));
    }
}