package com.supplify.supplier_to_company.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRegistrationDto {
    String name;
    String gstNumber;
    String panNumber;
    String address;
    String contactPersonName;
    String contactEmail;
    String contactPhone;
    String password;
    String country;
    String industryCategory;
    String businessType;
    Integer yearOfEstablishment;
    Integer numberOfEmployees;
    String bankAccountNumber;
    String ifscOrSwift;
    String bankName;
    String branchAddress;
    String shippingCapabilities;
    String paymentTerms;
    String annualRevenueRange;
}
