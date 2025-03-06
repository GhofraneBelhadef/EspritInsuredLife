package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract_Holder;
import com.example.donationmanagement.services.ContractManagement.IContractHolderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion Contract_Holder")
@RestController
@RequestMapping("/contractHolders")
public class ContractHolderController {
    @Autowired
    private IContractHolderService contractholderService;

    @PostMapping("/add")
    public Contract_Holder addContractHolder(@RequestBody Contract_Holder contractholder) {
        return contractholderService.add(contractholder);
    }

    @PutMapping("/update")
    public Contract_Holder updateContractHolder(@RequestBody Contract_Holder contractholder) {
        return contractholderService.update(contractholder);
    }

    @DeleteMapping("/remove/{id}")
    public void removeContractHolder(@PathVariable long id) {
        contractholderService.remove(id);
    }

    @GetMapping("/all")
    public List<Contract_Holder> getAllContractHolders() {
        return contractholderService.getAll();
    }

    @GetMapping("/{id}")
    public Contract_Holder getContractHolderById(@PathVariable long id) {
        return contractholderService.getById(id);
    }



}
