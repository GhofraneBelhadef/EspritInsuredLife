package com.example.donationmanagement.services.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Loan;
import java.util.List;

public interface ILoanService {
    Loan addLoan(Loan loan);
    Loan getLoanById(Long id);
    List<Loan> getAllLoans();
    void updateLoan(Long id, Loan loanDetails);
    void deleteLoan(Long id);
    boolean checkLoanApproval(Double creditHistory, Double income, Integer dependent);
}
