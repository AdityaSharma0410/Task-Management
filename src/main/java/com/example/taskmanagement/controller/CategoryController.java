package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {
    @Autowired
    CategoryService categoryservice;
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        // Implement the logic to create a category
        return categoryservice.createCategory(category);
    }
    @GetMapping
    public List<Category> listAllCategories()
    {
        return categoryservice.listAllCategories();
    }
}
