package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "po_status_history")
public class POStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    String status;
    @ManyToOne
    User changedBy;
    LocalDateTime timeStamp;
}
