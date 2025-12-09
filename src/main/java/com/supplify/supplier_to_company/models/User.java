package com.supplify.supplier_to_company.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(unique = true)
    String email;
    String phoneNumber;
    String status;
    String password;
    String userType;
    @ManyToMany
    List<Role> roles;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
