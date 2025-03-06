package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract_Accounting;
import com.example.donationmanagement.services.ContractManagement.IContractAccountingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion ContractAccounting")
@RestController
@RequestMapping("/contractAccounting")
public class ContractAccountingController {
    @Autowired
    private IContractAccountingService contractAccountingService;
    @PostMapping("/add")
    public Contract_Accounting add(@RequestBody Contract_Accounting contractAccounting) {
        return contractAccountingService.add(contractAccounting);
    }

    @GetMapping("/all")
    public List<Contract_Accounting> getAllContractAccounting() {
        return contractAccountingService.getAll();
    }
    @PutMapping("/update/{id}")
    public Contract_Accounting updateContractAccounting(@RequestBody Contract_Accounting contract_accounting) {
        return contractAccountingService.update(contract_accounting);
    }
    @GetMapping("/{id}")
    public Contract_Accounting getContractAccountingById(@PathVariable long id) {
        return contractAccountingService.getById(id);
    }
}
