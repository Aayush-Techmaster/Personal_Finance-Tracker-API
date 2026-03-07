package com.FinanceTracker.PFT.Services.impl;

import com.FinanceTracker.PFT.Entities.UserLogin;
import com.FinanceTracker.PFT.Repository.UserRepo;
import com.FinanceTracker.PFT.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserLogin registerUser(UserLogin user) {
       if(userRepo.existsByEmail(user.getEmail())){
           throw new RuntimeException("Email already registered!!");
       }
       return userRepo.save(user);
    }

    @Override
    public List<UserLogin> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserLogin getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("User not found with email: " + email));
    }
}
