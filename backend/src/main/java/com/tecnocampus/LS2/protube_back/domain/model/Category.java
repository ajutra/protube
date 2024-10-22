package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;

public record Category(
        String name
) {
    static public Category from(StoreCategoryCommand command) {
        return new Category(command.categoryName());
    }
}
