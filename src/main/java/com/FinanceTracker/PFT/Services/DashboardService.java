package com.FinanceTracker.PFT.Services;

import com.FinanceTracker.PFT.Dtos.DashboardDTO;
import com.FinanceTracker.PFT.Dtos.DashboardResponse;
import com.FinanceTracker.PFT.Dtos.PortfolioRequest;
import com.FinanceTracker.PFT.Entities.Portfolio;
import com.FinanceTracker.PFT.Entities.UserLogin;

import java.util.Optional;


public interface DashboardService {


    Optional<DashboardDTO> getDashboard(String email);
    void saveCategoryTransaction(String email, Long amount, String type);
     DashboardResponse getOrCreatePortfolio(String email) ;

    void saveCategoryTransaction(PortfolioRequest portfolioRequest);
}
