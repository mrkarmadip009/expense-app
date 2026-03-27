package com.expense.expense_app.entity;

import javax.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    // ADD THIS MANUALLY TO FIX THE COMPILER ERROR
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}