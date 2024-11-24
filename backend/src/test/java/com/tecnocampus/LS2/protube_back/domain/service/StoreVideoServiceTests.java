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
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void setUp()  {
        MockitoAnnotations.openMocks(this);
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
    void storeVideoWithInvalidVideoFileExtension() {
        MockMultipartFile videoFile = new MockMultipartFile("videoFile", "video.txt", "text/plain", "video content".getBytes());
        MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", "thumbnail.png", "image/png", "thumbnail content".getBytes());
        String title = "Test Title";
        String description = "Test Description";
        String username = "TestUser";

        assertThrows(IOException.class, () -> storeVideoService.storeVideo(videoFile, thumbnailFile, title, description, username));
    }

    @Test
    void storeVideoWithInvalidThumbnailFileExtension() {
        MockMultipartFile videoFile = new MockMultipartFile("videoFile", "video.mp4", "video/mp4", "video content".getBytes());
        MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", "thumbnail.txt", "text/plain", "thumbnail content".getBytes());
        String title = "Test Title";
        String description = "Test Description";
        String username = "TestUser";

        assertThrows(IOException.class, () -> storeVideoService.storeVideo(videoFile, thumbnailFile, title, description, username));
    }
}