package com.example.donationmanagement.services.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, ID> {
    T save(T entity);  // Pour créer ou mettre à jour une entité
    Optional<T> getById(ID id);  // Récupérer par ID
    Page<T> getAll(Pageable pageable);  // Récupérer tous les objets
    T update(ID id, T entity, MultipartFile photo, MultipartFile cin, MultipartFile justificatifDomicile,
             MultipartFile rib, MultipartFile bulletinSalaire, MultipartFile declarationSante, MultipartFile designationBeneficiaire, MultipartFile photoProfil);
    void delete(ID id);  // Supprimer
    List<User> getAllDonors();
}
