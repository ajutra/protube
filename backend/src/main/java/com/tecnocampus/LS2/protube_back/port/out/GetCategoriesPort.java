package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Category;

import java.util.List;

public interface GetCategoriesPort {
    List<Category> getAllCategories();
}
