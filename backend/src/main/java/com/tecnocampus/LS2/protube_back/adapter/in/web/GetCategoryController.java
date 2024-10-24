package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCategoriesUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetCategoryController {
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;

    @GetMapping("/categories")
    public List<GetCategoryCommand> getAllCategories() {
        return getAllCategoriesUseCase.getAllCategories();
    }
}
