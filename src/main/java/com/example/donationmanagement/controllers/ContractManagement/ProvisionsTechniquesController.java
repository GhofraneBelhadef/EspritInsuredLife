package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.services.ContractManagement.ProvisionsTechniquesService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provisions")
public class ProvisionsTechniquesController {


        private final ProvisionsTechniquesService provisionsTechniquesService;

        @Autowired
        public ProvisionsTechniquesController(ProvisionsTechniquesService provisionsTechniquesService) {
            this.provisionsTechniquesService = provisionsTechniquesService;
        }
    }

