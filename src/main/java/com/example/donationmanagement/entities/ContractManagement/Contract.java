package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.*;
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
    private Insurrance_Type insurrance_type;

    @Enumerated(EnumType.STRING)
    private Category_contract category_contract;
    private Date Policy_inception_date;
    private Date expiration_date;
    private float monthly_price;

    @Enumerated(EnumType.STRING)
    private  Status status;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "contract")
    private Set<ContractHolder> contract_holders;
    @ManyToOne
    @JoinColumn(name = "contract_accounting_id")
    ContractAccounting contractAccounting;



}

