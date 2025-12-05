package com.supplify.supplier_to_company.repositories;

import com.supplify.supplier_to_company.models.SupplierDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierDocumentRepository extends JpaRepository<SupplierDocument, UUID> {
}
