package com.example.donationmanagement.controllers.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Loan;
import com.example.donationmanagement.services.LoanManagement.ILoanService;
import com.example.donationmanagement.DTO.LoanRequest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
@Validated
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @Autowired
    private RestTemplate restTemplate;

    // Create a new loan
    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) {
        Loan savedLoan = loanService.addLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);
    }

    // Apply for a loan with ML model integration
    @PostMapping("/apply-for-loan")
    public ResponseEntity<String> applyForLoan(@Valid @RequestBody LoanRequest loanRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Prepare request payload for Flask API
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("gender", loanRequest.getGender());
        requestPayload.put("married", loanRequest.getMarried());
        requestPayload.put("dependents", loanRequest.getDependents());
        requestPayload.put("education", loanRequest.getEducation());
        requestPayload.put("self_employed", loanRequest.getSelf_employed());
        requestPayload.put("applicant_income", loanRequest.getApplicant_income());
        requestPayload.put("coapplicant_income", loanRequest.getCoapplicant_income());
        requestPayload.put("loan_amount", loanRequest.getLoan_amount());
        requestPayload.put("loan_amount_term", loanRequest.getLoan_amount_term());
        requestPayload.put("credit_history", loanRequest.getCredit_history());
        requestPayload.put("property_area", loanRequest.getProperty_area());
        // Log the payload being sent
        try {
            System.out.println("Payload envoyé: " + new ObjectMapper().writeValueAsString(requestPayload));
        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion du payload en JSON: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in payload conversion");
        }

        // Set headers to ensure Flask recognizes the payload as JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send POST request to Flask server with payload and headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestPayload, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:5000/submit", HttpMethod.POST, entity, String.class);

        // Check response and interpret accordingly
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Loan application approved.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan application rejected.");
        }
    }



    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PutMapping("/{id}")
    public void updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
//        return loanService.updateLoan(id, loanDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }


}
