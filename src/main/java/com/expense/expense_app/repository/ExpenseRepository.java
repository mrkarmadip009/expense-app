package com.expense.expense_app.repository;

import com.expense.expense_app.entity.*;
import com.expense.expense_app.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
}