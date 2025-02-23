package com.example.donationmanagement.entities.LoanManagement;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="loan")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    private Long loan_id;


=======
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
>>>>>>> origin/Loan_Management
}

