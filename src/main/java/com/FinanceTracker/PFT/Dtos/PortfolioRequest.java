package com.FinanceTracker.PFT.Dtos;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

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
