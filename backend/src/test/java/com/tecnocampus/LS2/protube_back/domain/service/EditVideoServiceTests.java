package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.EditVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditVideoServiceTests {

    @Mock
    private StoreTagService storeTagService;

    @Mock
    private StoreCategoryService storeCategoryService;

    @Mock
    private GetVideoService getVideoService;

    @Mock
    private EditVideoPort editVideoPort;

    @InjectMocks
    private EditVideoService editVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editVideoSuccessfully() {
        String videoId = "1";
        EditVideoCommand editVideoCommand = new EditVideoCommand(videoId, "Updated Title", "Updated Description");
        Video video = new Video(videoId, 640, 480, 200, "Original Title", "Original Description", "user1", "videoFileName", "thumbnailFileName");

        when(getVideoService.getVideoById(videoId)).thenReturn(video);
        when(storeTagService.storeAndGetTag(any(StoreTagCommand.class))).thenReturn(new Tag("Tag1"));
        when(storeCategoryService.storeAndGetCategory(any(StoreCategoryCommand.class))).thenReturn(new Category("Category1"));

        editVideoService.editVideo(editVideoCommand, videoId);

        assertEquals("Updated Title", video.getTitle());
        assertEquals("Updated Description", video.getDescription());
        verify(editVideoPort).editVideo(any(Video.class));
    }

    @Test
    void editVideoThrowsExceptionWhenVideoNotFound() {
        String videoId = "nonExistentId";
        EditVideoCommand editVideoCommand = new EditVideoCommand(videoId, "Updated Title", "Updated Description");

        when(getVideoService.getVideoById(videoId)).thenThrow(new NoSuchElementException("Video not found"));

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> editVideoService.editVideo(editVideoCommand, videoId));

        assertEquals("Video not found", exception.getMessage());
    }

    @Test
    void processTagCommandsListReturnsEmptySetWhenNull() {
        Set<Tag> tags = editVideoService.processTagCommandsList(null);
        assertTrue(tags.isEmpty());
    }

    @Test
    void processCategoryCommandsListReturnsEmptySetWhenNull() {
        Set<Category> categories = editVideoService.processCategoryCommandsList(null);
        assertTrue(categories.isEmpty());
    }

    @Test
    void processTagCommandsListSuccessfully() {
        StoreTagCommand tagCommand = new StoreTagCommand("Tag1");
        when(storeTagService.storeAndGetTag(tagCommand)).thenReturn(new Tag("Tag1"));

        Set<Tag> tags = editVideoService.processTagCommandsList(List.of(tagCommand));

        assertEquals(1, tags.size());
        assertTrue(tags.contains(new Tag("Tag1")));
    }

    @Test
    void processCategoryCommandsListSuccessfully() {
        StoreCategoryCommand categoryCommand = new StoreCategoryCommand("Category1");
        when(storeCategoryService.storeAndGetCategory(categoryCommand)).thenReturn(new Category("Category1"));

        Set<Category> categories = editVideoService.processCategoryCommandsList(List.of(categoryCommand));

        assertEquals(1, categories.size());
        assertTrue(categories.contains(new Category("Category1")));
    }

    @Test
    void processTagCommandsListHandlesException() {
        StoreTagCommand tagCommand = new StoreTagCommand("Tag1");
        when(storeTagService.storeAndGetTag(tagCommand)).thenThrow(new IllegalArgumentException("Tag already exists"));

        Set<Tag> tags = editVideoService.processTagCommandsList(List.of(tagCommand));

        assertEquals(1, tags.size());
        assertTrue(tags.contains(new Tag("Tag1")));
    }

    @Test
    void processCategoryCommandsListHandlesException() {
        StoreCategoryCommand categoryCommand = new StoreCategoryCommand("Category1");
        when(storeCategoryService.storeAndGetCategory(categoryCommand)).thenThrow(new IllegalArgumentException("Category already exists"));

        Set<Category> categories = editVideoService.processCategoryCommandsList(List.of(categoryCommand));

        assertEquals(1, categories.size());
        assertTrue(categories.contains(new Category("Category1")));
    }
}