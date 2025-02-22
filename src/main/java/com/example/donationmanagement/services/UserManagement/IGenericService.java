package com.example.donationmanagement.services.UserManagement;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, ID> {
    T save(T entity);  // Pour créer ou mettre à jour une entité
    Optional<T> getById(ID id);  // Récupérer par ID
    List<T> getAll();  // Récupérer tous les objets
    T update(ID id, T entity, MultipartFile photo, MultipartFile cin, MultipartFile justificatifDomicile,
             MultipartFile rib, MultipartFile bulletinSalaire, MultipartFile declarationSante, MultipartFile designationBeneficiaire, MultipartFile photoProfil);
    void delete(ID id);  // Supprimer
}
