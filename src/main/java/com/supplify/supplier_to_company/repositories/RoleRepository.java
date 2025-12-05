package com.supplify.supplier_to_company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplify.supplier_to_company.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    public Role findByName(String name);

    @Query(
            value = "SELECT * FROM roles WHERE name LIKE CONCAT(:orgName, '_%')",
            nativeQuery = true
    )
    public List<Role> getRolesByName(String orgName);

}
