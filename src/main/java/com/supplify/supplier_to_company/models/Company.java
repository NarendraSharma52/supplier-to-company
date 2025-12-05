package com.supplify.supplier_to_company.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "companies")
public class Company {
    @Id
    UUID id;
    String name;
    String legalName;
    String gstNumber;
    String registrationNumber;
    String companyType;
    String headquartersAddress;
    String contactEmail;
    Long contactPhone;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
