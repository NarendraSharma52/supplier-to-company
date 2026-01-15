package com.supplify.supplier_to_company.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InviteSupplierEmployeeDto {

    private String email;
    private String password;
    private String firstName;
    private String phoneNumber;
    private String lastName;
    private List<UUID> roleIds;

}
