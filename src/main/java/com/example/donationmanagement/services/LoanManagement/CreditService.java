package com.example.donationmanagement.services.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Credit;
import com.example.donationmanagement.repositories.LoanManagement.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService implements ICreditService {

    @Autowired
    private CreditRepository creditRepository;

   // @Override
    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    //@Override
    public Optional<Credit> getCreditById(Long id) {
        return creditRepository.findById(id);
    }

    //@Override
    public Credit saveCredit(Credit credit) {
        return creditRepository.save(credit);
    }

   // @Override
    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }
}
