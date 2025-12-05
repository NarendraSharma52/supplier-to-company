package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "good_receipts")
public class GoodReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    @ManyToOne
    Shipment shipment;
    @ManyToOne
    User receivedBy;
    LocalDateTime receiptDate;
    String status;
    @OneToMany
    List<WorkNote> workNotes;
    @OneToMany
    List<Document> documents;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}