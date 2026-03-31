package com.expense.expense_app.controller;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses") // Sabhi URLs /api/expenses se shuru honge
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    // Constructor Injection (Best Practice)
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // 1. Saare Expenses dekhne ke liye (GET)
    // URL: http://localhost:8080/api/expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // 2. Naya Expense save karne ke liye (POST)
    // URL: http://localhost:8080/api/expenses/save
    @PostMapping("/save")
    public Expense addExpense(@RequestBody Expense expense) {
        // @RequestBody Postman se aane wale JSON ko Java object mein badalta hai
        return expenseRepository.save(expense);
    }
}