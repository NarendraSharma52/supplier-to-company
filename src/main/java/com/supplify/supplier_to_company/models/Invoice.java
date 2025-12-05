package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String invoiceNumber;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    @ManyToOne
    Supplier supplier;
    LocalDate invoiceDate;
    LocalDate dueDate;
    String status;
    String currency;
    Double subTotalAmount;
    @OneToMany
    List<WorkNote> workNotes;
    @OneToMany
    List<Document> documents;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
