package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.SupplierUser;
import com.supplify.supplier_to_company.models.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class NotifactionService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // ✅ Method to process HTML template
    public String getHtmlTemplate(Context context, String templateName) {
        return templateEngine.process(templateName, context);
    } 

    // ✅ Email sending method
    public void inviteEmployeeEmail(SupplierUser invitee, User inviter) {
        try {
            Context context = new Context();
            context.setVariable("employeeName", invitee.getFirstName());
            context.setVariable("organization", invitee.getSupplier().getName());
            context.setVariable("inviterName", inviter.getEmail());
            context.setVariable("employeeEmail", invitee.getEmail());
            context.setVariable("invitationLink", "https://www.google.com");

            // Generate HTML from Thymeleaf template
            String htmlContent = getHtmlTemplate(context, "invite-template");

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject("Invitation to join SupplyNest on behalf of "
                    + invitee.getSupplier().getName());
            helper.setTo(invitee.getEmail());
            helper.setText(htmlContent, true); // true = HTML

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}