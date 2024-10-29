package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCategoriesUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesService implements GetAllCategoriesUseCase {
    private final GetCategoryPort getCategoryPort;

    @Override
    public List<GetCategoryCommand> getAllCategories() {
        List<Category> categories = getCategoryPort.getAllCategories();

        return categories.stream()
                .map(GetCategoryCommand::from)
                .collect(Collectors.toList());
    }
}
