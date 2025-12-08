package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_users")
public class CompanyUser extends User {
    @ManyToOne
    Company company;
    String fullName;


}
