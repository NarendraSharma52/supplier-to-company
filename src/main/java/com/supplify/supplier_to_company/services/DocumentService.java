package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.exceptions.UploadFileException;
import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.SupplierDocument;
import com.supplify.supplier_to_company.repositories.SupplierDocumentRepository;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@Service
public class DocumentService {

    @Autowired
    ImageKitService imageKitService;
    @Autowired
    SupplierDocumentRepository supplierDocumentRepository;

    public SupplierDocument uploadSupplierDocument(Supplier supplier, String fileType, MultipartFile file){
        try{
            log.info("Uploading {} document for supplier ID: {}", fileType, supplier.getId());
            String folder = "/suppliers/" + supplier.getId().toString();
            String fileName = file.getOriginalFilename();
            if(fileType.equals("companyLogo")){
                fileName = "companyLogo";
            }
            Result result =  imageKitService.uploadFile(file, fileName, folder);
            SupplierDocument supplierDocument = new SupplierDocument();
            supplierDocument.setDocumentType(fileType);
            supplierDocument.setUploadedAt(LocalDateTime.now());
            supplierDocument.setCreatedAt(LocalDateTime.now());
            supplierDocument.setSupplier(supplier);
            supplierDocument.setStatus("ACTIVE");
            supplierDocument.setFileUrl(result.getUrl());
            supplierDocument.setUpdatedAt(LocalDateTime.now());
            SupplierDocument saved = supplierDocumentRepository.save(supplierDocument);
            log.info("Successfully saved {} document for supplier ID: {}", fileType, supplier.getId());
            return saved;
        }catch (UploadFileException e){
            log.error("Failed to upload {} document for supplier ID: {}", fileType, supplier.getId(), e);
            throw new RuntimeException("Failed to upload " + fileType + " document", e);
        }catch (Exception e){
            log.error("Unexpected error while uploading {} document for supplier ID: {}", fileType, supplier.getId(), e);
            throw new RuntimeException("Unexpected error uploading " + fileType + " document", e);
        }
    }


}
