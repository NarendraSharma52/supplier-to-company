package com.supplify.supplier_to_company.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "po_acknowledgments")
public class POAcknowledgement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    PurchaseOrder purchaseOrder;
    @ManyToOne
    Supplier supplier;
    String status;
    @OneToMany
    List<WorkNote> workNotes;
    LocalDate updatedDeliveryDate;
    @OneToMany
    List<Document> attachments;
}
