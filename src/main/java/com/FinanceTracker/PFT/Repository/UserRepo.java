package com.FinanceTracker.PFT.Repository;

import com.FinanceTracker.PFT.Entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserLogin,Long> {

    Optional<UserLogin> findByEmail(String email);

    boolean existsByEmail(String Email);

}
