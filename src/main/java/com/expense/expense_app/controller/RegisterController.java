package com.expense.expense_app.controller;

import com.expense.expense_app.entity.User;
import com.expense.expense_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // 👉 Open register page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html
    }

    // 👉 Handle form submit
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }
}