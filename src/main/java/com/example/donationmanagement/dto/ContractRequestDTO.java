package com.example.donationmanagement.dto;


public class ContractRequestDTO {
    private String fullname;
    private String email;
    private String phone;
    private String contractType;
    public ContractRequestDTO() {}

    public ContractRequestDTO(String fullname, String email, String phone, String contractType) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.contractType = contractType;
        // "worker", "employé", "business"
    }

    // Getters and Setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}


