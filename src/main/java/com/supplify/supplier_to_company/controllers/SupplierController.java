package com.supplify.supplier_to_company.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplify.supplier_to_company.dtos.InviteSupplierEmployeeDto;
import com.supplify.supplier_to_company.dtos.SupplierRegistrationDto;
import com.supplify.supplier_to_company.exceptions.UnAuthorizedException;
import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.services.AuthService;
import com.supplify.supplier_to_company.services.SupplierService;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.Authorization;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/s2c/api/v1/supplier")
public class SupplierController {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private AuthService authService;



    @PostMapping("/start-registration")
    public ResponseEntity startRegistration(
            @RequestPart(value = "gstCertificate") MultipartFile gstCertificate,
            @RequestPart(value = "panCard") MultipartFile panCard,
            @RequestPart(value = "businessLicense", required = false) MultipartFile businessLicense,
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

    @PostMapping("/invite-employee")
    public ResponseEntity<?> inviteEmployee(
            @RequestBody InviteSupplierEmployeeDto inviteSupplierEmployeeDto,
            @RequestHeader("Authorization") String authorization) {

        boolean isAccessAvailable =
                authService.isAccessAvailableByToken(
                        authorization,
                        "invite-employee"
                );

        if (!isAccessAvailable) {
            throw new UnAuthorizedException(
                    "User is not authorized to invite an employee"
            );
        }

        // Your invitation business logic
        supplierService.inviteSupplierEmployee(
                inviteSupplierEmployeeDto,
                authService.getUserByToken(authorization));

        return ResponseEntity.ok("Employee invitation sent successfully");
    }

    @GetMapping("/get-employee")
    public ResponseEntity getUsersLastSixMonths(@RequestParam int value,
                                                @RequestParam String unit){
        try{
            return ResponseEntity.ok(supplierService.getUserByDate(value, unit));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     @GetMapping("/get-supllier")
    public ResponseEntity getSupplierBtwDDates(@RequestParam String startDate,
                                               @RequestParam String endDate){
        try{
            LocalDate start=LocalDate.parse(startDate);
            LocalDate end=LocalDate.parse(endDate);
            return ResponseEntity.ok(supplierService.findUser(start,end));

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    // @GetMapping("/pages")
    // public Page<User> getUserByPage(@RequestParam int pages,
      //                              @RequestParam int size){
       // return supplierService.getPage((pages,size));
    //}

    @PatchMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable UUID id,
                                     @RequestBody Map<String,Object> update){
        return ResponseEntity.ok(supplierService.updateUser(id,update));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(UUID id){
        return supplierService.deleteUser(id);
    }

    @DeleteMapping("/deletename/{id}")
    public String deleteName(UUID id){
        supplierService.deleteName(id);
        return "User name succesfully";
    }









}






