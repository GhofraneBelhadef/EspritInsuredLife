package com.example.donationmanagement.entities.ContractManagement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class ContractHolder {
    public enum Holder_Type {
        Primary , Secondary
    }
    public enum Status {
        Active,Expired,Resillied
    }

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contractholder_id;
    private  Holder_Type holder_type;
    private String holder_name ;
    private String relationshiptoinsurance ;

    @Enumerated(EnumType.STRING)
    private  Status status;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    Contract contract;


}