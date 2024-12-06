package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StoreVideoServiceTests {

    @Mock
    private StoreVideoPort storeVideoPort;

    @Mock
    private StoreTagService storeTagService;

    @Mock
    private StoreCategoryService storeCategoryService;

    @Mock
    private GetUserService getUserService;

    @Mock
    private GetVideoService getVideoService;

    @Mock
    private StoreCommentService storeCommentService;

    @InjectMocks
    private StoreVideoService storeVideoService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Field storageDirField = StoreVideoService.class.getDeclaredField("storageDir");
        storageDirField.setAccessible(true);
        storageDirField.set(storeVideoService, "test-storage-dir");
    }

    @Test
    void storeVideoWhenUserExistsAndVideoDoesNotExist() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");
        Video video = TestObjectFactory.createDummyVideo("video1", user);

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        when(getVideoService.getVideoByTitleAndUsername(anyString(), anyString())).thenThrow(NoSuchElementException.class);
        when(storeVideoPort.storeAndGetVideo(any(Video.class), anySet(), anySet())).thenReturn(video);

        storeVideoService.storeVideo(command);

        verify(storeVideoPort).storeAndGetVideo(any(Video.class), anySet(), anySet());
    }

    @Test
    void storeVideoWhenUserExistsAndVideoAlreadyExists() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");
        Video video = TestObjectFactory.createDummyVideo("video1", user);

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        when(getVideoService.getVideoByTitleAndUsername(anyString(), anyString())).thenReturn(video);

        assertThrows(IllegalArgumentException.class, () -> storeVideoService.storeVideo(command));
    }

    @Test
    void storeVideoWhenUserDoesNotExist() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");

        when(getUserService.getUserByUsername(anyString())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> storeVideoService.storeVideo(command));
    }

    @Test
    void storeVideoWithTagsAndCategories() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");
        Video video = TestObjectFactory.createDummyVideo("video1", user);
        Tag tag = TestObjectFactory.createDummyTag("tag1");
        Category category = TestObjectFactory.createDummyCategory("category1");

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        when(getVideoService.getVideoByTitleAndUsername(anyString(), anyString())).thenThrow(NoSuchElementException.class);
        when(storeTagService.storeAndGetTag(any(StoreTagCommand.class))).thenReturn(tag);
        when(storeCategoryService.storeAndGetCategory(any(StoreCategoryCommand.class))).thenReturn(category);
        when(storeVideoPort.storeAndGetVideo(any(Video.class), anySet(), anySet())).thenReturn(video);

        storeVideoService.storeVideo(command);

        verify(storeTagService).storeAndGetTag(any(StoreTagCommand.class));
        verify(storeCategoryService).storeAndGetCategory(any(StoreCategoryCommand.class));
        verify(storeVideoPort).storeAndGetVideo(any(Video.class), anySet(), anySet());
        verify(storeCommentService).storeCommentFromStoreVideoService(any(StoreCommentCommand.class), any(Video.class));
    }

    @Test
    void processTagCommandsList_withNullInput_returnsEmptySet() {
        Set<Tag> tags = storeVideoService.processTagCommandsList(null);
        assertTrue(tags.isEmpty());
    }

    @Test
    void processCategoryCommandsList_withNullInput_returnsEmptySet() {
        Set<Category> categories = storeVideoService.processCategoryCommandsList(null);
        assertTrue(categories.isEmpty());
    }

    @Test
    void storeVideoWithFilesSuccessfully() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");
        Video video = Video.from(command, user);
        Files.createDirectory(Paths.get("test-storage-dir"));

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        doNothing().when(storeVideoPort).storeVideo(any(), anySet(), anySet());

        storeVideoService.storeVideoWithFiles(file, thumbnail, command);

        verify(storeVideoPort).storeVideo(any(), anySet(), anySet());
        assertTrue(Files.exists(Paths.get("test-storage-dir", video.getVideoFileName())));
        assertTrue(Files.exists(Paths.get("test-storage-dir", video.getThumbnailFileName())));

        Files.deleteIfExists(Paths.get("test-storage-dir", video.getVideoFileName()));
        Files.deleteIfExists(Paths.get("test-storage-dir", video.getThumbnailFileName()));
        Files.deleteIfExists(Paths.get("test-storage-dir"));
    }

    @Test
    void storeVideoWithFilesThrowsIOException() throws IOException {
        MockMultipartFile file = spy(new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        doAnswer(invocation -> { throw new IOException("Test IOException"); }).when(file).getInputStream();

        assertThrows(RuntimeException.class, () -> storeVideoService.storeVideoWithFiles(file, thumbnail, command));
    }

    @Test
    void storeVideoWithFilesThrowsUnexpectedException() throws IOException {
        MockMultipartFile file = spy(new MockMultipartFile("file", "video.mp4", "video/mp4", "video content".getBytes()));
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        doAnswer(invocation -> { throw new RuntimeException("Test Unexpected Exception"); }).when(file).getInputStream();

        assertThrows(RuntimeException.class, () -> storeVideoService.storeVideoWithFiles(file, thumbnail, command));
    }

    @Test
    void resolveFileNameConflictReturnsSamePathIfNoConflict() throws IOException {
        Path path = Paths.get("test-storage-dir", "video.mp4");
        Files.createDirectory(Paths.get("test-storage-dir"));

        Path resolvedPath = storeVideoService.resolveFileNameConflict(path);

        assertEquals(path, resolvedPath);

        Files.deleteIfExists(path);
        Files.deleteIfExists(Paths.get("test-storage-dir"));
    }

    @Test
    void resolveFileNameConflictResolvesConflict() throws IOException {
        Path path = Paths.get("test-storage-dir", "video.mp4");
        Files.createDirectory(Paths.get("test-storage-dir"));
        Files.createFile(path);
        Path conflictingPath = Paths.get("test-storage-dir", "video_1.mp4");
        Files.createFile(conflictingPath);

        Path resolvedPath = storeVideoService.resolveFileNameConflict(path);

        assertEquals(Paths.get("test-storage-dir", "video_2.mp4"), resolvedPath);

        Files.deleteIfExists(path);
        Files.deleteIfExists(conflictingPath);
        Files.deleteIfExists(resolvedPath);
        Files.deleteIfExists(Paths.get("test-storage-dir"));
    }

    @Test
    void stripExtensionReturnsFileNameWithoutExtension() {
        String fileName = "video.mp4";
        String strippedFileName = storeVideoService.stripExtension(fileName);

        assertEquals("video", strippedFileName);
    }

    @Test
    void stripExtensionReturnsFileNameIfNoExtension() {
        String fileName = "video";
        String strippedFileName = storeVideoService.stripExtension(fileName);

        assertEquals("video", strippedFileName);
    }

    @Test
    void getFileExtensionReturnsExtension() {
        String fileName = "video.mp4";
        String extension = storeVideoService.getFileExtension(fileName);

        assertEquals(".mp4", extension);
    }

    @Test
    void getFileExtensionReturnsEmptyStringIfNoExtension() {
        String fileName = "video";
        String extension = storeVideoService.getFileExtension(fileName);

        assertEquals("", extension);
    }

    @Test
    void storeVideoWhenCommentsArePresentAndTagsCategoriesThrowExceptionButAreProcessedAnyways() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("video1");
        User user = TestObjectFactory.createDummyUser("user1");
        Video video = TestObjectFactory.createDummyVideo("video1", user);

        when(getUserService.getUserByUsername(anyString())).thenReturn(user);
        when(getVideoService.getVideoByTitleAndUsername(anyString(), anyString())).thenThrow(NoSuchElementException.class);
        when(storeTagService.storeAndGetTag(any(StoreTagCommand.class))).thenThrow(IllegalArgumentException.class);
        when(storeCategoryService.storeAndGetCategory(any(StoreCategoryCommand.class))).thenThrow(IllegalArgumentException.class);
        when(storeVideoPort.storeAndGetVideo(any(Video.class), anySet(), anySet())).thenReturn(video);

        storeVideoService.storeVideo(command);

        verify(storeCommentService, times(1)).storeCommentFromStoreVideoService(any(StoreCommentCommand.class), eq(video));
    }
}