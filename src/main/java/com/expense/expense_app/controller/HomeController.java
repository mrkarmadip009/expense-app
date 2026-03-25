package com.expense.expense_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Expense App Backend Running 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "API is working ✅";
    }
}