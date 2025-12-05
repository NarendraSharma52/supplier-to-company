package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.SupplierUser;
import com.supplify.supplier_to_company.repositories.SupplierUserRepository;

import java.util.UUID;

@Service
public class SupplierUserService {

    @Autowired
    SupplierUserRepository supplierUserRepository;

    public SupplierUser save(SupplierUser supplierUser){
        return supplierUserRepository.save(supplierUser);
    }

    public SupplierUser findById(UUID id){
        return supplierUserRepository.findById(id).orElse(null);
    }

    public Supplier getSuplierByUser(User user){
        SupplierUser supplierUser = this.findById(user.getId());
        return supplierUser.getSupplier();
    }

}

