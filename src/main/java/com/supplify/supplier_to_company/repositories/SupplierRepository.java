package com.supplify.supplier_to_company.repositories;

import com.supplify.supplier_to_company.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    public  Supplier findById(int  id);

    void deleteById(int id);

    List<Supplier> findByCreatedAtBefore(LocalDateTime time);


}
