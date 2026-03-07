package com.FinanceTracker.PFT.Controllers;

import com.FinanceTracker.PFT.Dtos.DashboardResponse;
import com.FinanceTracker.PFT.Dtos.PortfolioRequest;
import com.FinanceTracker.PFT.Entities.Portfolio;
import com.FinanceTracker.PFT.Entities.UserLogin;
import com.FinanceTracker.PFT.Repository.PortfolioRepo;
import com.FinanceTracker.PFT.Services.DashboardService;
import com.FinanceTracker.PFT.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private PortfolioRepo portfolioRepo;

    @PostMapping("/register")
    public ResponseEntity<UserLogin> register(@RequestBody UserLogin user){
        UserLogin savedUser = userLoginService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserLogin> getUserEmail(@PathVariable String email){
        UserLogin user = userLoginService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable String email){
        DashboardResponse dashboard = dashboardService.getOrCreatePortfolio(email);
        return ResponseEntity.ok(dashboard);
    }

    @PostMapping("/{email}/income")
    public ResponseEntity<String> addIncome(@PathVariable String email, @RequestParam Long amount) {
        dashboardService.saveCategoryTransaction(email, amount, "INCOME");
        return ResponseEntity.ok("Income added successfully");
    }

    @PostMapping("/{email}/expense")
    public ResponseEntity<String> addExpense(@PathVariable String email, @RequestParam Long amount) {
        dashboardService.saveCategoryTransaction(email, amount, "EXPENSE");
        return ResponseEntity.ok("Expense added successfully");
    }

    @PostMapping("/{email}/assets")
    public ResponseEntity<String> addAssets(@PathVariable String email, @RequestParam Long amount) {
        dashboardService.saveCategoryTransaction(email, amount, "ASSETS");
        return ResponseEntity.ok("Assets added successfully");
    }

    @PostMapping("/{email}/liabilities")
    public ResponseEntity<String> addLiabilities(@PathVariable String email, @RequestParam Long amount) {
        dashboardService.saveCategoryTransaction(email, amount, "LIABILITIES");
        return ResponseEntity.ok("Liabilities added successfully");
    }


    @PostMapping("/addPortfolioValues")
    public ResponseEntity<String> addPortfolioValues(@RequestBody PortfolioRequest portfolioRequest){
        dashboardService.saveCategoryTransaction(portfolioRequest);
        return ResponseEntity.ok("Done with adding the values in portfolio");
    }
}
