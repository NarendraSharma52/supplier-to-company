package com.supplify.supplier_to_company.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supplify.supplier_to_company.models.Operation;
import com.supplify.supplier_to_company.repositories.OperationRepository;

@Service
public class OperationService {

    @Autowired
    OperationRepository operationRepository;

    List<String> supplierAdminOperations = Arrays.asList(
            "supplier_review",
            "supplier_approval",
            "supplier_rejection",
            "supplier_profile_update",
            "supplier_registration",
            "purchase_order_submit_for_approval",
            "purchase_order_cancel",
            "purchase_order_update",
            "shipment_create",
            "shipment_dispatch",
            "shipment_update",
            "invoice_create",
            "invoice_update",
            "invoice_submit_for_approval",
            "invite_employee",
            "see_all_available_roles"
    );

    public List<Operation> getAllSupplierRelatedOperations(){
        List<Operation> operations = new ArrayList<>();
        for(String operationName : supplierAdminOperations){
            // select * from operations where name = operationName
            Operation operation = operationRepository.findByName(operationName);
            operations.add(operation);
        }
        return operations;
    }
}
