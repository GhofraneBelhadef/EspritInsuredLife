package com.example.donationmanagement.services.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasNom(String nom) {
        return (root, query, criteriaBuilder) ->
                nom == null ? null : criteriaBuilder.like(root.get("nom"), "%" + nom + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> hasRole(User.Role role) {
        return (root, query, criteriaBuilder) ->
                role == null ? null : criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<User> hasTelephone(String telephone) {
        return (root, query, criteriaBuilder) ->
                telephone == null ? null : criteriaBuilder.like(root.get("telephone"), "%" + telephone + "%");
    }

    public static Specification<User> isActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                active == null ? null : criteriaBuilder.equal(root.get("active"), active);
    }
}
