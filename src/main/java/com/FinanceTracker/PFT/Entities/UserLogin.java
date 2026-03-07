package com.FinanceTracker.PFT.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String name;
    private String email;
    private String password;
    private String defaultCurrency;

    @JsonIgnore
    @OneToOne(mappedBy = "userLogin",cascade = CascadeType.ALL)
    private Portfolio portfolio;
}
