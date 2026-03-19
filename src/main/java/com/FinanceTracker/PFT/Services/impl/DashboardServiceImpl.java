package com.FinanceTracker.PFT.Services.impl;

import com.FinanceTracker.PFT.Dtos.DashboardDTO;
import com.FinanceTracker.PFT.Dtos.DashboardResponse;
import com.FinanceTracker.PFT.Dtos.PortfolioRequest;
import com.FinanceTracker.PFT.Entities.Portfolio;
import com.FinanceTracker.PFT.Entities.UserLogin;
import com.FinanceTracker.PFT.Repository.PortfolioRepo;
import com.FinanceTracker.PFT.Repository.UserRepo;
import com.FinanceTracker.PFT.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PortfolioRepo portfolioRepo;

    @Override
    public Optional<DashboardDTO> getDashboard(String email) {
        UserLogin user = userRepo.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User not found with id : "+ email)
        );

        Portfolio portfolio = portfolioRepo.findByUserLogin(user).orElseThrow(
                ()->new RuntimeException("Portfolio not found for user :"+user.getEmail())
        );

        return Optional.of(new DashboardDTO(
                portfolio.getIncome(),
                portfolio.getExpenses(),
                portfolio.getAssets(),
                portfolio.getLiabilities(),
                portfolio.getNetWorth()
        ));
    }
    void setAssets(Portfolio portfolio){
        Long savings = portfolio.getIncome() - portfolio.getExpenses();
        portfolio.setAssets(0L);
        portfolio.setAssets(portfolio.getAssets() + savings);
    }
    @Override
    public void saveCategoryTransaction(String email, Long amount, String type) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        Portfolio portfolio = portfolioRepo.findByUserLoginEmail(email)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));


        if ("INCOME".equals(type)) {
            portfolio.setIncome(portfolio.getIncome() + amount);
            portfolio.setCurrentBalance(portfolio.getIncome()-portfolio.getExpenses());


        } else if ("EXPENSE".equals(type)) {
            portfolio.setExpenses(portfolio.getExpenses() + amount);
            portfolio.setCurrentBalance(portfolio.getIncome()-portfolio.getExpenses());

        } else if ("ASSETS".equals(type)) {
            portfolio.setAssets(portfolio.getAssets()+amount);

        } else if ("LIABILITIES".equals(type)) {
            portfolio.setLiabilities(portfolio.getLiabilities() + amount);
        }

        portfolio.setNetWorth(portfolio.getAssets() - portfolio.getLiabilities() + portfolio.getCurrentBalance());
        portfolioRepo.save(portfolio);
    }

    @Override
    public DashboardResponse getOrCreatePortfolio(String email) {


        UserLogin user = userRepo.findByEmail(email).orElseThrow
                (()->new RuntimeException("user didnt found!!with email :"+email));

        Portfolio portfolio = portfolioRepo.findByUserLogin(user)
                .orElseGet(() -> {
                    Portfolio newPortfolio = new Portfolio();
                    newPortfolio.setUserLogin(user);
                    newPortfolio.setIncome(0L);
                    newPortfolio.setExpenses(0L);
                    newPortfolio.setAssets(0L);
                    newPortfolio.setLiabilities(0L);
                    newPortfolio.setCurrentBalance(0L);
                    newPortfolio.setNetWorth(0L);

                    return portfolioRepo.save(newPortfolio);
                });

        DashboardResponse dto = new DashboardResponse();
        dto.setPid(portfolio.getPid());
        dto.setEmail(user.getEmail());
        dto.setTotalIncome(portfolio.getIncome());
        dto.setTotalExpense(portfolio.getExpenses());
        dto.setTotalAssets(portfolio.getAssets());
        dto.setTotalLiabilities(portfolio.getLiabilities());
        dto.setCurrentBalance(portfolio.getCurrentBalance());
        dto.setNetWorth(portfolio.getNetWorth());


        return dto;


}
    @Override
    public void saveCategoryTransaction(PortfolioRequest portfolioRequest) {

        UserLogin user = userRepo.findByEmail(portfolioRequest.getEmail()).orElseThrow
                (()->new RuntimeException("user didnt found!!with email :"
                        +portfolioRequest.getEmail()));

        Portfolio portfolio = portfolioRepo.findByUserLogin(user)
                .orElseGet(() -> {
                    Portfolio newPortfolio = new Portfolio();
                    newPortfolio.setUserLogin(user);
                    newPortfolio.setIncome(portfolioRequest.getTotalIncome());
                    newPortfolio.setExpenses(portfolioRequest.getTotalExpense());
                    newPortfolio.setLiabilities(portfolioRequest.getTotalLiabilities());
                    newPortfolio.setAssets(portfolioRequest.getAssets());
                    newPortfolio.setCurrentBalance(newPortfolio.getIncome()- newPortfolio.getExpenses());
                    newPortfolio.setNetWorth(newPortfolio.getAssets() - newPortfolio.getLiabilities());

                    return portfolioRepo.save(newPortfolio);
                });

        portfolio.setIncome(portfolio.getIncome()+portfolioRequest.getTotalIncome());
        portfolio.setExpenses(portfolio.getExpenses()+portfolioRequest.getTotalExpense());
        portfolio.setLiabilities(portfolio.getLiabilities()+portfolioRequest.getTotalLiabilities());
        portfolio.setAssets(portfolio.getAssets()+portfolioRequest.getAssets());
        Long savings = portfolio.getIncome() - portfolio.getExpenses();
        portfolio.setCurrentBalance(portfolio.getCurrentBalance() + savings);
        portfolio.setNetWorth(portfolio.getAssets() - portfolio.getLiabilities() + portfolio.getCurrentBalance());

        portfolioRepo.save(portfolio);
    }
    
    
    @Override
    public void deleteAssetsAndLiabilities(String email, Long amount, String type){
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        Portfolio portfolio = portfolioRepo.findByUserLoginEmail(email)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        if("ASSETS".equals(type)){
            if(portfolio.getAssets()-amount>=0){
                portfolio.setAssets(portfolio.getAssets()-amount);
            }else{
                throw new IllegalArgumentException("Delete amount exceeds Assets");
            }

        } else if ("LIABILITIES".equals(type)){
            if(portfolio.getLiabilities() - amount >=0) {
                portfolio.setLiabilities(portfolio.getLiabilities() - amount);
            }else{
                throw new IllegalArgumentException("Delete amount exceeds liabilities");
            }

        }
        portfolio.setNetWorth(portfolio.getAssets() - portfolio.getLiabilities() + portfolio.getCurrentBalance());
        portfolioRepo.save(portfolio);

    }
}
