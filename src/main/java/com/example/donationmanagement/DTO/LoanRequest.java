package com.example.donationmanagement.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class LoanRequest {

    @NotNull(message = "Gender is required")
    private String gender;  // 'M' or 'F'

    @NotNull(message = "Marital status is required")
    private Integer married; // 1 if married, 0 if otherwise

    @NotNull(message = "Dependents count is required")
    private Integer dependents; // Number of dependents

    @NotNull(message = "Education level is required")
    private Integer education; // 0 for Graduate, 1 for Not Graduate

    @NotNull(message = "Self-employed status is required")
    private Integer self_employed; // 1 for self-employed, 0 otherwise

    @NotNull(message = "Applicant income is required")
    private Double applicant_income;

    @NotNull(message = "Coapplicant income is required")
    private Double coapplicant_income;

    @NotNull(message = "Loan amount is required")
    private Double loan_amount;

    @NotNull(message = "Loan amount term is required")
    private Integer loan_amount_term; // Term of loan in months

    @NotNull(message = "Credit history is required")
    private Integer credit_history; // 0 or 1 for credit history

    @NotNull(message = "Property area is required")
    private Integer property_area; // 0, 1, or 2 representing different property areas

    // Getters and Setters


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getMarried() {
        return married;
    }

    public void setMarried(Integer married) {
        this.married = married;
    }

    public Integer getDependents() {
        return dependents;
    }

    public void setDependents(Integer dependents) {
        this.dependents = dependents;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getSelf_employed() {
        return self_employed;
    }

    public void setSelf_employed(Integer self_employed) {
        this.self_employed = self_employed;
    }

    public Double getApplicant_income() {
        return applicant_income;
    }

    public void setApplicant_income(Double applicant_income) {
        this.applicant_income = applicant_income;
    }

    public Double getCoapplicant_income() {
        return coapplicant_income;
    }

    public void setCoapplicant_income(Double coapplicant_income) {
        this.coapplicant_income = coapplicant_income;
    }

    public Double getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(Double loan_amount) {
        this.loan_amount = loan_amount;
    }

    public Integer getLoan_amount_term() {
        return loan_amount_term;
    }

    public void setLoan_amount_term(Integer loan_amount_term) {
        this.loan_amount_term = loan_amount_term;
    }

    public Integer getCredit_history() {
        return credit_history;
    }

    public void setCredit_history(Integer credit_history) {
        this.credit_history = credit_history;
    }

    public Integer getProperty_area() {
        return property_area;
    }

    public void setProperty_area(Integer property_area) {
        this.property_area = property_area;
    }
}