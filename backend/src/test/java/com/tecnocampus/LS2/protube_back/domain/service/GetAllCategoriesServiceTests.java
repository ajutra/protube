package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoriesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllCategoriesServiceTests {

    @Mock
    private GetCategoriesPort getCategoriesPort;

    @InjectMocks
    private GetAllCategoriesService getCategoriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoriesReturnsListOfCategories() {
        Category category1 = TestObjectFactory.createDummyCategory("1");
        List<Category> categories = List.of(category1);
        List<GetCategoryCommand> categoryCommands =
                categories.stream()
                        .map(GetCategoryCommand::from)
                        .collect(Collectors.toList());

        when(getCategoriesPort.getAllCategories()).thenReturn(categories);

        List<GetCategoryCommand> result = getCategoriesService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals(categoryCommands.get(0).categoryName(), result.get(0).categoryName());
        verify(getCategoriesPort).getAllCategories();
    }

    @Test
    void getAllCategoriesReturnsEmptyListWhenNoCategories() {
        when(getCategoriesPort.getAllCategories()).thenReturn(List.of());

        List<GetCategoryCommand> result = getCategoriesService.getAllCategories();

        assertTrue(result.isEmpty());
    }
}
