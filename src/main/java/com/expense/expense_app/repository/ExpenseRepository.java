package com.expense.expense_app.repository;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.repository.ExpenseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // No need to write any code here for basic CRUD
    // JpaRepository already provides methods like:
    // findAll(), save(), deleteById(), findById()
}