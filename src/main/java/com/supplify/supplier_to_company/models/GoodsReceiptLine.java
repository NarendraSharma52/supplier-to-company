package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goods_receipt_line")
public class GoodsReceiptLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    GoodReceipt goodReceipt;
    @ManyToOne
    PurchaseOrderLine purchaseOrderLine;
    @OneToOne
    ShipmentLine shipmentLine;
    Double quantityReceived;
    Double quantityRejecting;
    String reasonForRejection;
    String uom;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
