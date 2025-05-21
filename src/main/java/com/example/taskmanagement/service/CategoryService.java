package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.save(category);
    }

    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
/*
package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    public Category createCategory(Category category) {
        // Implement the logic to create a category
        if(category == null)
        {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if(category.getName() == null || category.getName().isEmpty())
        {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.save(category);
    }

    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
*/
