package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryService {
    private final StoreCategoryPort storeCategoryPort;

    Category storeAndGetCategory(StoreCategoryCommand command) {
        return storeCategoryPort.storeAndGetCategory(Category.from(command));
    }
}
