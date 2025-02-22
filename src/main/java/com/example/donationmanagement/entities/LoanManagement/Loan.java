package com.example.donationmanagement.entities.LoanManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="loan")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    private Long loan_id;


}
