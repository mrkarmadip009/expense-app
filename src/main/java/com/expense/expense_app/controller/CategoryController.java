package com.expense.expense_app.controller;

import com.expense.expense_app.entity.Category;
import com.expense.expense_app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Saari Categories dekhne ke liye
    // URL: http://localhost:8080/api/categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 2. Nayi Category save karne ke liye
    // URL: http://localhost:8080/api/categories/save
    @PostMapping("/save")
    public Category saveCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
}