package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Set;
@ToString(exclude = {"contractAccounting", "contract_holders"})
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Contract {
    public enum Insurrance_Type {
        Life_Insurance, Non_lifeinsurance
    }
    public enum Category_contract {
        Employee , Worker,Business
    }
    public enum Status {
        Active,Expired,Resillied
    }

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type d'assurance est requis.")
    private Insurrance_Type insurrance_type;
    @Min(value = 10, message = "L'âge assuré doit être au moins de 10 ans.")
    @Max(value = 100, message = "L'âge assuré ne peut pas être supérieur à 100 ans.")
    private int insuredAge;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La catégorie de contrat est requise.")
    private Category_contract category_contract;
    @NotNull(message = "La date de début de la police est requise.")
    @Past(message = "La date de début de la police ne peut pas être dans le futur.")
    private Date Policy_inception_date;
    @NotNull(message = "La date d'expiration est requise.")
    @Future(message = "La date d'expiration doit être dans le futur.")
    private Date expiration_date;
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix mensuel doit être supérieur à 0.")
    private float monthly_price;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut du contrat est requis.")
    private  Status status;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "contract")
    private Set<Contract_Holder> contract_holders;
    @ManyToOne
    @JoinColumn(name = "contract_accounting_id")
    ContractAccounting contractAccounting;
    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL)
    private ProvisionsTechniques provisionsTechniques;



}



    // Getters and setters
