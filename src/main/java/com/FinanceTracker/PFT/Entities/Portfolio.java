package com.FinanceTracker.PFT.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private Long income;
    private Long expenses;
    private Long assets;
    private Long liabilities;
    private Long netWorth;
    private Long CurrentBalance;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserLogin userLogin;

}
