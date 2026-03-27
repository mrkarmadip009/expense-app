package com.expense.expense_app.controller;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.entity.User;
import com.expense.expense_app.repository.ExpenseRepository;
import com.expense.expense_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/expenses")
    public String showExpenses(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<Expense> userExpenses = expenseRepository.findByUser(user);
            model.addAttribute("expenses", userExpenses);
        }

        model.addAttribute("newExpense", new Expense());
        return "expenses";
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute("newExpense") Expense expense) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            expense.setUser(user);
            expenseRepository.save(expense);
        }
        return "redirect:/expenses";
    }
}