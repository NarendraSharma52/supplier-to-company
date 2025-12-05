package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipment_lines")
public class ShipmentLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Shipment shipment;
    @ManyToOne
    PurchaseOrderLine purchaseOrderLine;
    Double quantity;
    String uom;
    @ManyToOne
    Product product;
    String batchNumber;
    LocalDate manufacturingDate;
    LocalDate expiryDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}