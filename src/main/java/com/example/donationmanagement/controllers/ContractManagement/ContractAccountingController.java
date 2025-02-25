package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
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
    public ContractAccounting add(@RequestBody ContractAccounting contractAccounting) {
        return contractAccountingService.add(contractAccounting);
    }

    @GetMapping("/all")
    public List<ContractAccounting> getAllContractAccounting() {
        return contractAccountingService.getAll();
    }
    @PutMapping("/update/{id}")
    public ContractAccounting updateContractAccounting(@RequestBody ContractAccounting contract_accounting) {
        return contractAccountingService.update(contract_accounting);
    }
    @GetMapping("/{id}")
    public ContractAccounting getContractAccountingById(@PathVariable long id) {
        return contractAccountingService.getById(id);
    }
}
