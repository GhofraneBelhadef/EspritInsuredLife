package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.MortalityTable;
import com.example.donationmanagement.repositories.ContractManagement.MortalityTableRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MortalityTableService {
    @Autowired
    private  MortalityTableRepository mortalityTableRepository;

    @PostConstruct // Exécute cette méthode après le démarrage de l'application
    public void loadMortalityTable() {
        if (mortalityTableRepository.count() > 0) {
            return; // Si la table contient déjà des données, on ne recharge pas
        }

        try {
            String filePath = ResourceUtils.getFile("classpath:data/mortality_table.csv").getAbsolutePath();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) { // Ignorer l'en-tête
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                int age = Integer.parseInt(data[0].trim());
                float probability = Float.parseFloat(data[1].trim());

                MortalityTable mortalityData = new MortalityTable();
                mortalityData.setAge(age);
                mortalityData.setProbabilityOfDeath(probability);

                mortalityTableRepository.save(mortalityData);
            }

            reader.close();
            System.out.println("📌 Table de mortalité chargée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getProbabilityOfDeath(int age) {
        MortalityTable mortalityData = mortalityTableRepository.findByAge(age);
        return (mortalityData != null) ? mortalityData.getProbabilityOfDeath() : 0.0f;
    }
}
