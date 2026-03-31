package com.expense.expense_app.repository;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Ab ye query sahi se chalegi kyunki Expense entity mein 'user' field aa gaya hai
    List<Expense> findByUser(User user);
}