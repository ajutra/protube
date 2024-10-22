package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Category;

import java.util.List;

public record GetCategoryCommand(
        String categoryName
) {
    public static List<GetCategoryCommand> from(List<Category> categories) {
        return categories.stream()
                .map(category -> new GetCategoryCommand(category.name()))
                .toList();
    }
}
