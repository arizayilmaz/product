package com.aryil.productapi.service;

import com.aryil.productapi.repository.CategoryRepository;
import com.aryil.productapi.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    public Optional<Category> updateCategory(UUID id, String name, String description) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(name);
            category.setDescription(description);
            return categoryRepository.save(category);
        });
    }

    public boolean deleteCategory(UUID id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return true;
        }).orElse(false);
    }
}
