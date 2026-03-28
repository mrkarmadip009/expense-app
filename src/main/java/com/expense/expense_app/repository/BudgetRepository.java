package com.expense.expense_app.repository;

import com.expense.expense_app.entity.Budget;
import com.expense.expense_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
}