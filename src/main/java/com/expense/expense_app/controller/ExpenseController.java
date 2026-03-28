package com.expense.expense_app.controller;

import com.expense.expense_app.entity.*;
import com.expense.expense_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    @Autowired private ExpenseRepository expenseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BudgetRepository budgetRepository;

    @GetMapping("/expenses")
    public String listExpenses(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("expenses", expenseRepository.findByUser(user));
        model.addAttribute("expense", new Expense());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("budgets", budgetRepository.findByUser(user));
        return "expenses";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("categoryName") String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/expenses";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense, @RequestParam("categoryId") Long catId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        expense.setCategory(categoryRepository.findById(catId).orElse(null));
        expense.setUser(user);
        expenseRepository.save(expense);
        return "redirect:/expenses";
    }

    @PostMapping("/saveBudget")
    public String saveBudget(@RequestParam("amountLimit") Double limit,
                             @RequestParam("month") String month,
                             @RequestParam("categoryId") Long catId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Budget budget = new Budget();
        budget.setAmountLimit(limit);
        budget.setMonth(month);
        budget.setUser(user);
        budget.setCategory(categoryRepository.findById(catId).orElse(null));
        budgetRepository.save(budget);
        return "redirect:/expenses";
    }
}