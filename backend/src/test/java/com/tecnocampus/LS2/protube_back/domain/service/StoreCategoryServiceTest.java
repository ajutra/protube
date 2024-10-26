package com.tecnocampus.LS2.protube_back.domain.service;


import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        assertThrows(IllegalArgumentException.class, () -> storeCategoryService.storeCategory(command));

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

        assertThrows(RuntimeException.class, () -> storeCategoryService.storeCategory(command));

        verify(storeCategoryPort, times(1)).storeCategory(any(Category.class));
    }
}
