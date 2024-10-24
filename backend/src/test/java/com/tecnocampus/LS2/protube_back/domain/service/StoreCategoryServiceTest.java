package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StoreCategoryServiceTest {

    @Mock
    private StoreCategoryPort storeCategoryPort;

    @InjectMocks
    private StoreCategoryService storeCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeCategoryCallsPortMethod() {
        StoreCategoryCommand command = new StoreCategoryCommand("Sports");
        storeCategoryService.storeCategory(command);

        verify(storeCategoryPort, times(1)).storeCategory(any(Category.class));
    }

    @Test
    void storeAndGetCategoryCallsPortMethod() {
        StoreCategoryCommand command = new StoreCategoryCommand("Sports");
        storeCategoryService.storeAndGetCategory(command);

        verify(storeCategoryPort, times(1)).storeAndGetCategory(any(Category.class));
    }

    @Test
    void storeCategoryHandlesCategoryAlreadyExists() {
        StoreCategoryCommand command = new StoreCategoryCommand("Sports");
        doThrow(new IllegalArgumentException("Category already exists")).when(storeCategoryPort).storeCategory(any());

        try {
            storeCategoryService.storeCategory(command);
        } catch (IllegalArgumentException e) {
            assertEquals("Category already exists", e.getMessage());
        }

        verify(storeCategoryPort, times(1)).storeCategory(any(Category.class));
    }

    @Test
    void storeAndGetCategoryReturnsExpectedCategory() {
        StoreCategoryCommand command = new StoreCategoryCommand("Sports");
        Category expectedCategory = new Category("Sports");
        when(storeCategoryPort.storeAndGetCategory(any(Category.class))).thenReturn(expectedCategory);

        Category result = storeCategoryService.storeAndGetCategory(command);

        verify(storeCategoryPort, times(1)).storeAndGetCategory(any(Category.class));
        assertEquals(expectedCategory, result);
    }

    @Test
    void storeCategoryThrowsExceptionWhenPortFails() {
        StoreCategoryCommand command = new StoreCategoryCommand("Sports");
        doThrow(new RuntimeException("Database error")).when(storeCategoryPort).storeCategory(any());

        try {
            storeCategoryService.storeCategory(command);
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }

        verify(storeCategoryPort, times(1)).storeCategory(any(Category.class));
    }
}
