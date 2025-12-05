package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String mediaType;
    String mediaLink;
}