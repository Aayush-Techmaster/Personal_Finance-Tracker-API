package com.FinanceTracker.PFT.Entities;

import com.FinanceTracker.PFT.Enums.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;             // "Salary", "Food", "Rent"

    @Enumerated(EnumType.STRING)
    private CategoryType type;       // INCOME or EXPENSE

    private boolean isDefault;       // true = system created, false = user created

    private String icon;             // "🍕", "💰", "🏠"

    private String color;            // "#FF5733" for pie charts

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserLogin user;          // whose category (null if default/global)

}