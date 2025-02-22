package com.example.donationmanagement.entities.LoanManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLoan;
    @JsonProperty("loanAmount")
    private double loanAmount;


    @JsonProperty("loanTerm")
    private int loanTerm;
    @JsonProperty("status")
    private String status;
    @JsonProperty("interestRate")
    private double interestRate;


    //relation
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne
    @JoinColumn(name = "riskBreakdown_id")
    private RiskBreakdown riskBreakdown;

    @OneToOne
    @JoinColumn(name = "idCredit")
    private RiskBreakdown Credit;
}

