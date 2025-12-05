package com.supplify.supplier_to_company.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplify.supplier_to_company.dtos.SupplierRegistrationDto;
import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.services.SupplierService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/s2c/api/v1/supplier")
public class SupplierController {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/start-registration")
    public ResponseEntity startRegistration(
            @RequestPart(value = "gstCertificate") MultipartFile gstCertificate,
            @RequestPart(value = "panCard") MultipartFile panCard,
            @RequestPart(value = "businessLicense") MultipartFile businessLicense,
            @RequestPart(value = "isoCertificate") MultipartFile isoCertificate,
            @RequestPart(value = "msmeCertificate") MultipartFile msmeCertificate,
            @RequestPart(value = "insurancePapers") MultipartFile insurancePapers,
            @RequestPart(value = "companyLogo") MultipartFile companyLogo,
            @RequestPart(value = "supplierInformation") String supplierInfo
    ) {

        try {
            SupplierRegistrationDto supplierRegistrationDto = objectMapper.readValue(supplierInfo, SupplierRegistrationDto.class);

            Supplier supplier = supplierService.registerSupplier(
                    gstCertificate,
                    panCard,
                    businessLicense,
                    isoCertificate,
                    msmeCertificate,
                    insurancePapers,
                    companyLogo,
                    supplierRegistrationDto
            );

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Supplier registration started successfully");
            responseBody.put("referenceId", supplier.getReferenceId());

            return ResponseEntity.ok(responseBody);
        } catch (JsonProcessingException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid supplier information format");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process supplier registration");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }

    }
}
