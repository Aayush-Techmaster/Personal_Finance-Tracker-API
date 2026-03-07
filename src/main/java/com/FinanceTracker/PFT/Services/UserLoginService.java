package com.FinanceTracker.PFT.Services;


import com.FinanceTracker.PFT.Entities.UserLogin;
import com.FinanceTracker.PFT.Repository.UserRepo;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserLoginService {

    UserLogin registerUser(UserLogin user);
    List<UserLogin> getAllUsers();
    UserLogin getUserByEmail(String email);
}
