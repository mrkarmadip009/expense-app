package com.expense.expense_app.entity;

import javax.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amountLimit;
    private String month;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getAmountLimit() { return amountLimit; }
    public void setAmountLimit(Double limit) { this.amountLimit = limit; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Category getCategory() { return category; }
    public void setCategory(Category cat) { this.category = cat; }
}