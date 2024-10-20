package com.tecnocampus.LS2.protube_back.domain.service;

import static org.mockito.Mockito.*;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

public class StoreVideoServiceTests {

    @Mock
    private StoreVideoPort storeVideoPort;

    @Mock
    private StoreTagPort storeTagPort;

    @Mock
    private StoreCategoryPort storeCategoryPort;

    @InjectMocks
    private StoreVideoService storeVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeVideoSuccessfully() {
        StoreVideoCommand validCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        storeVideoService.storeVideo(validCommand);
        verify(storeVideoPort, times(1)).storeVideo(any(Video.class), anySet(), anySet(), anySet());
        verify(storeTagPort, times(1)).storeTag(any());
        verify(storeCategoryPort, times(1)).storeCategory(any());
    }

    @Test
    void storeVideoWithNullTagsAndCategories() {
        StoreVideoCommand commandWithNullTagsAndCategories = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", null, null
        );
        storeVideoService.storeVideo(commandWithNullTagsAndCategories);
        verify(storeVideoPort, times(1)).storeVideo(any(Video.class), eq(Set.of()), eq(Set.of()), anySet());
        verify(storeTagPort, never()).storeTag(any());
        verify(storeCategoryPort, never()).storeCategory(any());
    }

    @Test
    void storeVideoWithEmptyTagsAndCategories() {
        StoreVideoCommand commandWithEmptyTagsAndCategories = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of(), List.of()
        );
        storeVideoService.storeVideo(commandWithEmptyTagsAndCategories);
        verify(storeVideoPort, times(1)).storeVideo(any(Video.class), eq(Set.of()), eq(Set.of()), anySet());
        verify(storeTagPort, never()).storeTag(any());
        verify(storeCategoryPort, never()).storeCategory(any());
    }

    @Test
    void storeVideoWithNullDescription() {
        StoreVideoCommand commandWithNullDescription = new StoreVideoCommand(
                1920, 1080, 30, "title", null, "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );
        storeVideoService.storeVideo(commandWithNullDescription);
        verify(storeVideoPort, times(1)).storeVideo(any(Video.class), anySet(), anySet(), anySet());
        verify(storeTagPort, times(1)).storeTag(any());
        verify(storeCategoryPort, times(1)).storeCategory(any());
    }

    @Test
    void storeVideoWithNullFileNames() {
        StoreVideoCommand commandWithNullFileNames = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                null, null, List.of("tag"), List.of("category")
        );
        storeVideoService.storeVideo(commandWithNullFileNames);
        verify(storeVideoPort, times(1)).storeVideo(any(Video.class), anySet(), anySet(), anySet());
        verify(storeTagPort, times(1)).storeTag(any());
        verify(storeCategoryPort, times(1)).storeCategory(any());
    }

    @Test
    void storeVideoThrowsExceptionWhenUserNotFound() {
        //TODO
//        assertThrows(NoSuchElementException.class, () -> {
//            storeVideoService.storeVideo(validCommand);
//        });
    }

    @Test
    void storeVideoThrowsExceptionWhenVideoAlreadyExists() {
        //TODO
//        assertThrows(IllegalArgumentException.class, () -> {
//            storeVideoService.storeVideo(validCommand);
//        });
    }
}