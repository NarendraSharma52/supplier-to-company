package com.supplify.supplier_to_company.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
            "see_all_available_roles",
            "create_role"
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

    public  List<Operation> getAllOperationObjectByIds(List<UUID> ids){
        List<Operation> operations=new ArrayList<>();
        for(UUID id: ids){
            Operation operation=operationRepository.findById(id).orElse(null);
            if(operation==null){
                // throw Exception
            }
            operations.add(operation);
        }
        return  operations;
    }

}
