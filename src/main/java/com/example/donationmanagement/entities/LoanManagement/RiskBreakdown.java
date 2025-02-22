package com.example.donationmanagement.entities.LoanManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="risk_breakdown")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class RiskBreakdown {
    @Id
    private Long risk_breakdown_id;

   
}
