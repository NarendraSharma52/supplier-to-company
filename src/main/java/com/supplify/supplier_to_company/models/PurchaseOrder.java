package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(unique = true)
    String poNumber;
    @ManyToOne
    Supplier supplier;
    String status;
    LocalDate issuedDate;
    LocalDate expectedDeliveryDate;
    Double totalAmount;
    String currency;
    String paymentTerm;
    String incoTerm;
    @ManyToOne
    User createdBy;
    @ManyToOne
    User approvedBy;
    @OneToMany
    List<Document> additionalDocuments;
    @OneToMany
    List<WorkNote> workNotes;

}
