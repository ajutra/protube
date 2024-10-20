package com.tecnocampus.LS2.protube_back.domain.model;

public record Tag(
        String name
) {
    static public Tag from(String name) {
        return new Tag(name);
    }
}
