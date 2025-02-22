package com.example.donationmanagement.entities.LoanManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="customer")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private Long customer_id;


}
