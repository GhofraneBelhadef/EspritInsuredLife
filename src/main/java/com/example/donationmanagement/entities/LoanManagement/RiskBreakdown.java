package com.example.donationmanagement.entities.LoanManagement;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class RiskBreakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRiskBreakdown;
    private double riskScore ;
    private double riskProbability ;
    private int coefficient ;
    private double featureValue ;

    public long getIdRiskBreakdown() {
        return idRiskBreakdown;
    }

    public void setIdRiskBreakdown(long idRiskBreakdown) {
        this.idRiskBreakdown = idRiskBreakdown;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public double getRiskProbability() {
        return riskProbability;
    }

    public void setRiskProbability(double riskProbability) {
        this.riskProbability = riskProbability;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public double getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(double featureValue) {
        this.featureValue = featureValue;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

//relation

    @OneToOne(mappedBy = "riskBreakdown", cascade = CascadeType.ALL, orphanRemoval = true)
    private Loan loan;
}
