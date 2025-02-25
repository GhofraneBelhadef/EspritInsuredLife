package com.example.donationmanagement.services.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Loan;
import com.example.donationmanagement.repositories.LoanManagement.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public void updateLoan(Long id, Loan loanDetails) {
//        Loan loan = getLoanById(id); // Fetch existing loan
//
//        loan.setLoanAmount(loanDetails.getLoanAmount());
//        loan.setLoanTerm(loanDetails.getLoanTerm());
//        loan.setStatus(loanDetails.getStatus());
//        loan.setInterestRate(loanDetails.getInterestRate());
//        loan.setCustomer(loanDetails.getCustomer());
//        loan.setRiskBreakdown(loanDetails.getRiskBreakdown());
//
//        return loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(Long id) {
        Loan loan = getLoanById(id);
        loanRepository.delete(loan);
    }

    public boolean checkLoanApproval(Double creditHistory, Double income, Integer dependent) {
        String flaskApiUrl = "http://localhost:5000/submit";  // Flask API URL

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = new HashMap<>();
        request.put("credit_history", creditHistory);
        request.put("income", income);
        request.put("dependent", dependent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, entity, Map.class);

        return (Boolean) response.getBody().get("approval");
    }
}
