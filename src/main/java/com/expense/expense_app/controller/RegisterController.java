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

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {

        System.out.println("REGISTER HIT: " + user.getEmail()); // ✅ DEBUG

        userService.saveUser(user);
        return "login";
    }
}