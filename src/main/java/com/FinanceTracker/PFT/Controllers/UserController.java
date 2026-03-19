package com.FinanceTracker.PFT.Controllers;

import com.FinanceTracker.PFT.Dtos.DashboardResponse;
import com.FinanceTracker.PFT.Dtos.PortfolioRequest;
import com.FinanceTracker.PFT.Dtos.UserResponse;
import com.FinanceTracker.PFT.Entities.UserLogin;
import com.FinanceTracker.PFT.Services.DashboardService;
import com.FinanceTracker.PFT.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="*")
public class UserController {


    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private DashboardService dashboardService;



    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserLogin user){
        UserLogin saved = userLoginService.registerUser(user);
        UserResponse response = new UserResponse(saved.getUid(), saved.getName(), saved.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userLoginService.getAllUsers().stream()
                .map(u -> new UserResponse(u.getUid(), u.getName(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/me")
    public ResponseEntity<UserLogin> getUserEmail(Principal principal){
        String email = principal.getName();
        UserLogin user = userLoginService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(Principal principal){
        String email = principal.getName();
        DashboardResponse dashboard = dashboardService.getOrCreatePortfolio(email);
        return ResponseEntity.ok(dashboard);
    }

    @PostMapping("/income")
    public ResponseEntity<String> addIncome(Principal principal, @RequestParam Long amount) {
        String email = principal.getName();
        dashboardService.saveCategoryTransaction(email, amount, "INCOME");
        return ResponseEntity.ok("Income added successfully");
    }

    @PostMapping("/expense")
    public ResponseEntity<String> addExpense(Principal principal, @RequestParam Long amount) {
        String email = principal.getName();
        dashboardService.saveCategoryTransaction(email, amount, "EXPENSE");
        return ResponseEntity.ok("Expense added successfully");
    }

    @PostMapping("/assets")
    public ResponseEntity<String> addAssets(Principal principal, @RequestParam Long amount) {
        String email = principal.getName();
        dashboardService.saveCategoryTransaction(email, amount, "ASSETS");
        return ResponseEntity.ok("Assets added successfully");
    }

    @PostMapping("/liabilities")
    public ResponseEntity<String> addLiabilities(Principal principal, @RequestParam Long amount) {
        String email = principal.getName();
        dashboardService.saveCategoryTransaction(email, amount, "LIABILITIES");
        return ResponseEntity.ok("Liabilities added successfully");
    }

    @DeleteMapping("/deleteAssets")
    public ResponseEntity<String> deleteAssets(Principal principal, @RequestParam Long amount){
        String email = principal.getName();
        dashboardService.deleteAssetsAndLiabilities(email, amount, "ASSETS");
        return ResponseEntity.ok("Assets deleted successfully");
    }
    @DeleteMapping("/deleteLiabilities")
    public ResponseEntity<String> deleteLiabilities(Principal principal, @RequestParam Long amount){
        String email = principal.getName();
        dashboardService.deleteAssetsAndLiabilities(email, amount, "LIABILITIES");
        return ResponseEntity.ok("Assets deleted successfully");
    }


    @PostMapping("/addPortfolioValues")
    public ResponseEntity<String> addPortfolioValues(Principal principal ,@RequestBody PortfolioRequest portfolioRequest){
        String tokenEmail = principal.getName();
        if (!tokenEmail.equals(portfolioRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only modify your own portfolio!");
        }
        dashboardService.saveCategoryTransaction(portfolioRequest);
        return ResponseEntity.ok("Done with adding the values in portfolio");
    }
}
