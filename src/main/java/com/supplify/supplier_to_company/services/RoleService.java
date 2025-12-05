package com.supplify.supplier_to_company.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import com.supplify.supplier_to_company.security.JwtUtil;
// import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supplify.supplier_to_company.models.Operation;
import com.supplify.supplier_to_company.models.Role;
import com.supplify.supplier_to_company.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    OperationService operationService;

    @Autowired
    RoleRepository roleRepository;


    // IndianOil -> Will try to register as supplier into our application
    // IndianOil -> We will be creating one admin user.
    // Now we need to create one user for indian oil which will have admin role.
    // IndianOil is a supplier -> and its admin role should have all the operations related to supplier

    public Role createAdminRoleForSupplier(String supplierCompanyName){
        // 789
        // 789-admin
        // all the operations related to supplier
        Role role = new Role();
        role.setName(supplierCompanyName + "_admin");
        role.setDescription("Admin role for supplier " + supplierCompanyName);
        List<Operation> operations = operationService.getAllSupplierRelatedOperations();
        role.setOperations(operations);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        return roleRepository.save(role);
    }

    public List<String> mapRoleToRoleNames(List<Role> roles){
        List<String> roleNames = new ArrayList<>();
        for(Role role : roles){
            roleNames.add(role.getName());
        }
        return roleNames;
    }

    public List<Role> getRoleByOrgName(String orgName){
        return roleRepository.getRolesByName(orgName);
    }

    public Role findByName(String roleName){
        return roleRepository.findByName(roleName);
    }


}
