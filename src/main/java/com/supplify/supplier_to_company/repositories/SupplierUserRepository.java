package com.supplify.supplier_to_company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplify.supplier_to_company.models.SupplierUser;

public interface SupplierUserRepository extends JpaRepository<SupplierUser, UUID> {


}
