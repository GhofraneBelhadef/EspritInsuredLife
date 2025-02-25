package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.services.ContractManagement.IContractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion Contract")
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private IContractService contractService;

    @PostMapping("/add")
    public Contract addContract(@RequestBody Contract contract) {
        return contractService.add(contract);
    }

    @PutMapping("/update")
    public Contract updateContract(@RequestBody Contract contract) {
        return contractService.update(contract);
    }

    @DeleteMapping("/remove/{id}")
    public void removeContract(@PathVariable long id) {
        contractService.remove(id);
    }

    @GetMapping("/all")
    public List<Contract> getAllContracts() {
        return contractService.getAll();
    }

    @GetMapping("/{id}")
    public Contract getContractById(@PathVariable long id) {
        return contractService.getById(id);
    }

}
