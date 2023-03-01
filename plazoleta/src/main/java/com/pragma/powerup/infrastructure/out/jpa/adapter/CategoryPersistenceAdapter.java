package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public CategoryModel createCategory(CategoryModel categoryModel) {
        CategoryEntity createdCategory = this.categoryRepository.save(
                this.categoryEntityMapper.toEntity(categoryModel)
        );
        return this.categoryEntityMapper.toModel(createdCategory);
    }

    @Override
    public CategoryModel getCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).orElse(null);
        return this.categoryEntityMapper.toModel(categoryEntity);
    }
}
