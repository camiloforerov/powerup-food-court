package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.CategoryModel;

public interface ICategoryPersistencePort {
    CategoryModel createCategory(CategoryModel categoryModel);
    CategoryModel getCategoryById(Long categoryId);
}
