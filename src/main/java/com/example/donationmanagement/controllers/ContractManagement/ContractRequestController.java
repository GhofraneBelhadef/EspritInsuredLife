package com.example.donationmanagement.controllers.ContractManagement;
import com.example.donationmanagement.dto.ContractRequestDTO;
import com.example.donationmanagement.services.ContractManagement.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/contract-request")
public class ContractRequestController {
    @Autowired
    private WhatsAppService whatsAppService;

    @PostMapping
    public String requestContract(@RequestBody ContractRequestDTO contractRequestDTO) {
        // Crée un message à envoyer à l'admin
        String message = "Nouvelle demande de contrat:\n" +
                "Nom: " + contractRequestDTO.getFullname() + "\n" +
                "Email: " + contractRequestDTO.getEmail() + "\n" +
                "Téléphone: " + contractRequestDTO.getPhone() + "\n" +
                "Type de contrat: " + contractRequestDTO.getContractType();

        // Appelle le service pour envoyer le message via WhatsApp
        whatsAppService.sendWhatsAppMessage(message);

        return "Demande envoyée avec succès!";
    }
}

