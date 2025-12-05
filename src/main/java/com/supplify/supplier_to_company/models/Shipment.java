package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    @ManyToOne
    Supplier supplier;
    String shipFromAddress;
    String shipToAddress;
    LocalDate shipmentDate;
    LocalDate expectedDeliveryDate;
    String status;
    String transporterName;
    String trackingId;
    @OneToMany
    List<Document> attachments;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
