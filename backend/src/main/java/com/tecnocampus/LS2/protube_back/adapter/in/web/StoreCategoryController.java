package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCategoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreCategoryController {
    private final StoreCategoryUseCase storeCategoryUseCase;

    @PostMapping("/categories")
    public ResponseEntity<Void> storeCategory(@Valid @RequestBody StoreCategoryCommand storeCategoryCommand) {
        storeCategoryUseCase.storeCategory(storeCategoryCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
