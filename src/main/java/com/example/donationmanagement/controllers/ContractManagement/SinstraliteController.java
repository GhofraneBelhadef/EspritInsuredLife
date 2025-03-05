package com.example.donationmanagement.controllers.ContractManagement;
import com.example.donationmanagement.services.ContractManagement.SinistraliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sinistralite")
public class SinstraliteController {
    @Autowired
    private  SinistraliteService sinistraliteService;

    public SinstraliteController(SinistraliteService sinistraliteService) {
        this.sinistraliteService = sinistraliteService;
    }

    @GetMapping("/{category}")
    public float getTaux(@PathVariable String category) {
        return sinistraliteService.getTauxSinistralite(category);
    }

}
