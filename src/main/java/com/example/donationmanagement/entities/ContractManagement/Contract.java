package com.example.donationmanagement.entities.ContractManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Table(name = "contract")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Contract {
    @Id
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
    private Status status;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donation> donations; // Donations made to this contract

    @ManyToOne
    @JoinColumn(name = "contract_accounting_id")
    private Contract_Accounting contract_accounting;
}