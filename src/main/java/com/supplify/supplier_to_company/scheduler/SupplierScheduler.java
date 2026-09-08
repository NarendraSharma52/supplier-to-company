package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.SupplierUser;
import com.supplify.supplier_to_company.repositories.SupplierRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvitationService {

    private final SupplierRepository supplierRepository;
    private final NotifactionService notifactionService;

    public InvitationService(
            SupplierRepository supplierRepository,
            NotifactionService notifactionService) {

        this.supplierRepository = supplierRepository;
        this.notifactionService = notifactionService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendPendingInvitations() {

        LocalDateTime twentyFourHoursAgo =
                LocalDateTime.now().minusHours(24);

        List<SupplierUser> supplierUsers =
                supplierRepository.findByCreatedAtBefore(
                        twentyFourHoursAgo
                );

        for (SupplierUser supplierUser : supplierUsers) {

            Supplier invitee = supplierUser.getSupplier();
            String inviter = supplierUser.getFirstName();

            notifactionService.inviteEmployeeEmail(
                    invitee,
                    inviter
            );
        }
    }
}