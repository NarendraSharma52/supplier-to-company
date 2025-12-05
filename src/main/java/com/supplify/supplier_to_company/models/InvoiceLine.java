package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "invoice_lines")
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Invoice invoice;
    @ManyToOne
    PurchaseOrderLine purchaseOrderLine;
    @OneToOne
    ShipmentLine shipmentLine;
    @OneToOne
    GoodReceipt goodReceipt;
    Double quantityInvoices;
    Double unitPrice;
    Double discount;
    Double tax;
    Double totalAmount;
    String uom;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
