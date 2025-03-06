package com.example.donationmanagement.entities.DonationManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Donation {
    @Id
    private Long donation_id;

    private int donor_id;

    private double donation_amount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date donation_date;
    private StatusDonation StatusDonation;

    public enum StatusDonation{
        Pending,
        Completed,
        Cancelled
    }
    @OneToOne(mappedBy = "donation")
    private Reward reward;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

}
