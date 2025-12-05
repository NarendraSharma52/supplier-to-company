package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "supplier_documents")
public class SupplierDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Supplier supplier;
    String documentType;
    String fileUrl;
    LocalDate expiryDate;
    String status;
    LocalDateTime uploadedAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
