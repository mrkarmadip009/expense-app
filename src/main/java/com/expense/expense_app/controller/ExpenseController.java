package com.expense.expense_app.controller;

import com.expense.expense_app.entity.*;
import com.expense.expense_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String saveExpense(@ModelAttribute("expense") Expense expense,
                              @RequestParam("categoryId") Long catId,
                              Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Category category = categoryRepository.findById(catId).orElse(null);

        // --- BUDGET VALIDATION LOGIC START ---
        // Check karo ki is user ne is category ke liye koi budget set kiya hai?
        List<Budget> userBudgets = budgetRepository.findByUser(user);
        Budget categoryBudget = userBudgets.stream()
                .filter(b -> b.getCategory().getId().equals(catId))
                .findFirst()
                .orElse(null);

        if (categoryBudget != null) {
            // Is category ka purana total kharcha calculate karo
            Double currentSpent = expenseRepository.findByUser(user).stream()
                    .filter(e -> e.getCategory() != null && e.getCategory().getId().equals(catId))
                    .mapToDouble(Expense::getAmount)
                    .sum();

            // Agar (Purana Kharcha + Naya Kharcha) > Budget Limit
            if ((currentSpent + expense.getAmount()) > categoryBudget.getAmountLimit()) {
                // Stop saving and show error
                model.addAttribute("error", "Error: The budget for this category is being exceeded." + categoryBudget.getAmountLimit());

                // Refresh data for the page
                model.addAttribute("expenses", expenseRepository.findByUser(user));
                model.addAttribute("categories", categoryRepository.findAll());
                model.addAttribute("budgets", budgetRepository.findByUser(user));
                model.addAttribute("expense", expense);
                return "expenses";
            }
        }
        // --- BUDGET VALIDATION LOGIC END ---

        expense.setCategory(category);
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