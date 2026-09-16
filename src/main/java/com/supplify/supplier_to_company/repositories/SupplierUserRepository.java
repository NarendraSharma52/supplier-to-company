package com.supplify.supplier_to_company.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import com.supplify.supplier_to_company.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.supplify.supplier_to_company.models.SupplierUser;

public interface SupplierUserRepository extends JpaRepository<SupplierUser, UUID> {

    public List<SupplierUser> findByCreatedAtAfter(LocalDateTime date);

    public List<SupplierUser> findByCreatedAtBefore(LocalDateTime time);
}