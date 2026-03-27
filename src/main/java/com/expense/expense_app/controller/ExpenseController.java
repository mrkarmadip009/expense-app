package com.expense.expense_app.controller;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.entity.User;
import com.expense.expense_app.repository.ExpenseRepository;
import com.expense.expense_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/expenses")
    public String showExpenses(Model model) {
        User user = userRepository.findAll().get(0);

        List<Expense> list = expenseRepository.findByUser(user);
        model.addAttribute("expenses", list);

        return "expenses";
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestParam String title,
                             @RequestParam double amount) {

        User user = userRepository.findAll().get(0);

        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setDate(LocalDate.now());
        expense.setUser(user);

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return "redirect:/expenses";
    }
}