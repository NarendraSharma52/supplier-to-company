package com.supplify.supplier_to_company.dtos;

import com.supplify.supplier_to_company.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private UUID userId;
    private List<Role> roles;
    private long expiresAt;
    private String email;
    private String userType;
}
