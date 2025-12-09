package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.dtos.SupplierRegistrationDto;
import com.supplify.supplier_to_company.exceptions.UploadFileException;
import com.supplify.supplier_to_company.models.*;
import com.supplify.supplier_to_company.repositories.SupplierRepository;
import com.supplify.supplier_to_company.utilities.MappingUtilities;
import io.imagekit.sdk.models.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {


    @Autowired
    DocumentService documentService;

    @Autowired
    SupplierUserService supplierUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    MappingUtilities mappingUtilities;

    @Autowired
    SupplierRepository supplierRepository;

    public Supplier registerSupplier(
            MultipartFile gstCertificate,
            MultipartFile panCard,
            MultipartFile businessLicense,
            MultipartFile isoCertificate,
            MultipartFile msmeCertificate,
            MultipartFile insurancePapers,
            MultipartFile companyLogo,
            SupplierRegistrationDto supplierRegistrationDto
    ){
        // 1. Map the supplierRegistrationDto to supplier
        Supplier supplier = mappingUtilities.mapToSupplier(supplierRegistrationDto);
        // 2. Save the supplier
        supplier = saveSupplier(supplier);
        // 3. Create admin role for the supplier
        Role adminRole = roleService.createAdminRoleForSupplier(supplier.getName());
        // 4. Create admin user for the supplier
        SupplierUser supplierUser = new SupplierUser();
        supplierUser.setFirstName(supplier.getName());
        supplierUser.setLastName("Admin");
        supplierUser.setPassword(supplierRegistrationDto.getPassword());
        supplierUser.setSupplier(supplier);
        supplierUser.setEmail(supplier.getContactEmail());
        supplierUser.setPhoneNumber(supplier.getContactPhone());
        supplierUser.setStatus("ACTIVE");
       supplierUser.setUserType("SUPPLIER_USER");

        supplierUser.setRoles(List.of(adminRole));
        supplierUser.setCreatedAt(LocalDateTime.now());
        supplierUser.setUpdatedAt(LocalDateTime.now());
        supplierUserService.save(supplierUser);

        // Upload the documents to imageKit
        documentService.uploadSupplierDocument(
                supplier,
                "gstCertificate",
                gstCertificate
        );

        documentService.uploadSupplierDocument(
                supplier,
                "panCard",
                panCard
        );

        documentService.uploadSupplierDocument(
                supplier,
                "businessLicense",
                businessLicense
        );

        documentService.uploadSupplierDocument(
                supplier,
                "isoCertificate",
                isoCertificate
        );

        documentService.uploadSupplierDocument(
                supplier,
                "msmeCertificate",
                msmeCertificate
        );

        documentService.uploadSupplierDocument(
                supplier,
                "insurancePapers",
                insurancePapers
        );

        documentService.uploadSupplierDocument(
                supplier,
                "companyLogo",
                companyLogo
        );

        return supplier;
    }

    public String getSupplierNameBySupplierUser(User user){
        Supplier supplier = supplierUserService.getSuplierByUser(user);
        return supplier.getName();
    }

    public Supplier saveSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }
}
