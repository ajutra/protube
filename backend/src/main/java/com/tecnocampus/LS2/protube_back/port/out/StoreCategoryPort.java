package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Category;

public interface StoreCategoryPort {
    void storeCategory(Category category);
}
