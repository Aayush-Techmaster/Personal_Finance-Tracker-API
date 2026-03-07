package com.FinanceTracker.PFT.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private Long totalIncome;
    private Long totalExpense;
    private Long totalAssets;
    private Long totalLiabilities;
    private Long netWorth;
}
