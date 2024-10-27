package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetCategoryUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryService implements GetCategoryUseCase {
    private final GetCategoryPort getCategoryPort;

    @Override
    public GetCategoryCommand getCategory(String categoryName) {
        return GetCategoryCommand.from(getCategoryPort.getCategory(categoryName));
    }
}
