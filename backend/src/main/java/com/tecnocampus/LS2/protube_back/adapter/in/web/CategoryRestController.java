package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCategoriesUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetCategoryUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCategoryUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryRestController {
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final StoreCategoryUseCase storeCategoryUseCase;

    @GetMapping("/categories")
    public List<GetCategoryCommand> getAllCategories() {
        return getAllCategoriesUseCase.getAllCategories();
    }

    @GetMapping("/categories/{categoryName}")
    public GetCategoryCommand getCategory(@Valid @NotBlank @PathVariable String categoryName) {
        return getCategoryUseCase.getCategory(categoryName);
    }

    @PostMapping("/categories")
    public ResponseEntity<Void> storeCategory(@Valid @RequestBody StoreCategoryCommand storeCategoryCommand) {
        storeCategoryUseCase.storeCategory(storeCategoryCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
