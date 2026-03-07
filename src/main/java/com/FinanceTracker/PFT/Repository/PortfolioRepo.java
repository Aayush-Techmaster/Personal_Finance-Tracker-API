package com.FinanceTracker.PFT.Repository;

import com.FinanceTracker.PFT.Entities.Portfolio;
import com.FinanceTracker.PFT.Entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {

    Optional<Portfolio> findByUserLogin(UserLogin userLogin);
    Optional<Portfolio> findByUserLoginEmail(String email);
}
