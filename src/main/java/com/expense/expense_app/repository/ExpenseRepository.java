package com.expense.expense_app.repository;

import com.expense.expense_app.entity.Expense;
import com.expense.expense_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.user = :user")
    List<Expense> findByUser(@Param("user") User user);
}