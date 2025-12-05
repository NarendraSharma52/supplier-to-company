package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    @Column(unique = true)
    String gstNumber;
    @Column(unique = true)
    String panNumber;
    String address;
    String contactPersonName;
    @Column(unique = true)
    String contactEmail;
    @Column(unique = true)
    String contactPhone;
    String status;
    String reviewStatus;
    Boolean isActive;
    String country;
    String industryCategory;
    String businessType;
    Integer yearOfEstablishment;
    Integer numberOfEmployees;
    // Banking
    String bankAccountNumber;
    String ifscOrSwift;
    String bankName;
    String branchAddress;
    // Additional info
    String shippingCapabilities;
    String paymentTerms;
    String annualRevenueRange;
    // Reference ID like SUP-YYYYMM-XXXX
    @Column(unique = true)
    String referenceId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
