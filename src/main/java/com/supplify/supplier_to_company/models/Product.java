package com.supplify.supplier_to_company.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Supplier supplier;
    String name;
    String description;
    String uom;
    String basePrice;
    String availableQuantity;
    String leadTimeDays;
    String isActive;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
