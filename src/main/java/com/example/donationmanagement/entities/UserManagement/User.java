package com.example.donationmanagement.entities.UserManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private String nom;

    @Column(nullable = false)

    private String prenom;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)


    private String username;

    @Column(nullable = true, unique = true)
    private String telephone;

    private LocalDate dateNaissance;  // ✅ Remplace `Date` par `LocalDate` (plus moderne)



    @Column(nullable = false)
    private boolean active = false;
    @Column(unique = true)
    private String verificationToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp  // ✅ Ajoute automatiquement la date de création
    @Column(name = "date_creation", updatable = false)
    private LocalDate dateCreation;


    public enum Role {
        CLIENT, ADMIN
    }

    @Lob
    private byte[] cin;

    @Lob
    private byte[] justificatifDomicile;

    @Lob
    private byte[] rib;

    @Lob
    private byte[] bulletinSalaire;

    @Lob
    private byte[] declarationSante;

    @Lob
    private byte[] designationBeneficiaire;
    @Lob
    private byte[] photoProfil;
    @Column(name = "reset_token", unique = true)
    private String resetToken;
    @Column(unique = true)
    private String googleId;
    @Column(nullable = false)
    private boolean profilComplet = false;

}
