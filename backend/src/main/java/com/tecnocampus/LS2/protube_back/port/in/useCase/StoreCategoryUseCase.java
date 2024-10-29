package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;

public interface StoreCategoryUseCase {
    void storeCategory(StoreCategoryCommand storeCategoryCommand);
}
