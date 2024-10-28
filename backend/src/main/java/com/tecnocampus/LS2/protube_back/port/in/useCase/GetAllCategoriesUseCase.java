package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;

import java.util.List;

public interface GetAllCategoriesUseCase {
    List<GetCategoryCommand> getAllCategories();
}
