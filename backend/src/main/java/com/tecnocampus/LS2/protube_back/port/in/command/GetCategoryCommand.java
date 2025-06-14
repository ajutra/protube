package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Category;

public record GetCategoryCommand(
        String categoryName
) {
    public static GetCategoryCommand from(Category category) {
        return new GetCategoryCommand(category.name());
    }
}
