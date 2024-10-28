package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCategoryUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryService implements StoreCategoryUseCase {
    private final StoreCategoryPort storeCategoryPort;

    Category storeAndGetCategory(StoreCategoryCommand command) {
        return storeCategoryPort.storeAndGetCategory(Category.from(command));
    }

    @Override
    public void storeCategory(StoreCategoryCommand storeCategoryCommand) {
        storeCategoryPort.storeCategory(Category.from(storeCategoryCommand));
    }

}
