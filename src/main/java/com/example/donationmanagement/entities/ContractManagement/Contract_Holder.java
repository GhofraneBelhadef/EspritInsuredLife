package com.example.donationmanagement.entities.ContractManagement;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Contract_Holder {
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
    @JsonBackReference("contract-holder")
    Contract contract;


}
