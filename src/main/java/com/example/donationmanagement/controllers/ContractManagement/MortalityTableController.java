package com.example.donationmanagement.controllers.ContractManagement;
import com.example.donationmanagement.services.ContractManagement.MortalityTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mortality")

public class MortalityTableController {

    private final MortalityTableService mortalityTableService;

    // Assurez-vous d'avoir un constructeur avec @Autowired
    @Autowired
    public MortalityTableController(MortalityTableService mortalityTableService) {
        this.mortalityTableService = mortalityTableService;
    }

    @GetMapping("/{age}")
    public double getProbability(@PathVariable int age) {
        return mortalityTableService.getProbabilityOfDeath(age);
    }
}
