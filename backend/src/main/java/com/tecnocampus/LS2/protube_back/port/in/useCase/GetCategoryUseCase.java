package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;

public interface GetCategoryUseCase {
    GetCategoryCommand getCategory(String categoryName);
}
