package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTests {

    @Test
    void fromCommandCreatesCategoryWithCorrectName() {
        StoreCategoryCommand command = TestObjectFactory.createDummyStoreCategoryCommand("category_name");
        Category category = Category.from(command);
        assertEquals(command.categoryName(), category.name());
    }
}