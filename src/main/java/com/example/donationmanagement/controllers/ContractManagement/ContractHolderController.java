package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractHolder;
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
    public ContractHolder addContractHolder(@RequestBody ContractHolder contractholder) {
        return contractholderService.add(contractholder);
    }

    @PutMapping("/update")
    public ContractHolder updateContractHolder(@RequestBody ContractHolder contractholder) {
        return contractholderService.update(contractholder);
    }

    @DeleteMapping("/remove/{id}")
    public void removeContractHolder(@PathVariable long id) {
        contractholderService.remove(id);
    }

    @GetMapping("/all")
    public List<ContractHolder> getAllContractHolders() {
        return contractholderService.getAll();
    }

    @GetMapping("/{id}")
    public ContractHolder getContractHolderById(@PathVariable long id) {
        return contractholderService.getById(id);
    }



}
