package com.FinanceTracker.PFT.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioRequest {

    @NonNull
    private String email;
    private Long totalIncome;
    private Long totalExpense;
    private Long totalLiabilities;
}
