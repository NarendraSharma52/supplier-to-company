package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

// Indigo -> Indian Oil (PO)

// POL1. Petrol -> 1000
// POL2. HydrogenFuel ->> 500
@Entity
@Table(name = "purchase_order_lines")
public class PurchaseOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    @ManyToOne
    Product product;
    Double quantity;
    String uom;
    Double unitPrice;
    Double totalPrice;
    Double gst;
    @OneToMany
    List<DeliverySchedule> deliverySchedules;
    @OneToMany
    List<WorkNote> workNotes;
}

