package com.supplify.supplier_to_company.scheduler;

import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.SupplierUser;
import com.supplify.supplier_to_company.repositories.SupplierUserRepository;
import com.supplify.supplier_to_company.services.NotifactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierScheduler {

    private final SupplierUserRepository supplierUserRepository;
    private final NotifactionService notifactionService;

    public SupplierScheduler(
            SupplierUserRepository supplierUserRepository,
            NotifactionService notifactionService) {

        this.supplierUserRepository = supplierUserRepository;
        this.notifactionService = notifactionService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendPendingInvitations() {

        LocalDateTime twentyFourHoursAgo =
                LocalDateTime.now().minusHours(24);

        List<SupplierUser> supplierUsers =
                supplierUserRepository.findByCreatedAtBefore(
                        twentyFourHoursAgo
                );

        for (SupplierUser supplierUser : supplierUsers) {

            // Re-send the pending invitation email to this supplier user.
            notifactionService.inviteEmployeeEmail(
                    supplierUser,
                    supplierUser
            );
        }
    }
}