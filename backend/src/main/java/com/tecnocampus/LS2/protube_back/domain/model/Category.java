package com.tecnocampus.LS2.protube_back.domain.model;

public record Category(
        String name
) {
    static public Category from(String name) {
        return new Category(name);
    }
}
