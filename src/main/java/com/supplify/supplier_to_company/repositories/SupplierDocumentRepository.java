package com.supplify.supplier_to_company.repositories;

import com.supplify.supplier_to_company.models.SupplierDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierDocumentRepository extends JpaRepository<SupplierDocument, UUID> {


    @Query(value = "select * from supplier_documents where supplier_id = :supplierId and document_type = 'companyLogo' ", nativeQuery = true)
    public  SupplierDocument getSupplierCompanyLogo(UUID supplierId);

}

