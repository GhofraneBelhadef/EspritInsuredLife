package com.example.donationmanagement.entities.DonationManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.UserManagement.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "user_id")  // Cette colonne va être utilisée comme clé étrangère
    private User user;



}
