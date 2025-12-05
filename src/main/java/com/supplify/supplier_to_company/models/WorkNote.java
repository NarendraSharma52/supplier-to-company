package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_notes")
public class WorkNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String text;
    UUID userId;
    String userType;
}
