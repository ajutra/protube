package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record StoreCategoryCommand (
        @Valid
        @NotBlank
        String categoryName
){
        // This is a workaround to allow the deserializer to create a GetCategoryCommand object
        public static StoreCategoryCommand from(String categoryName) {
                return new StoreCategoryCommand(categoryName);
        }
}
