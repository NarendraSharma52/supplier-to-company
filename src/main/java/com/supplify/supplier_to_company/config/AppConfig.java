package com.supplify.supplier_to_company.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class AppConfig {


    @Value("${email.address}")
    String emailAddress;
    @Value("${email.password}")
    String emailPassword;

    @Bean
    public JavaMailSender generateJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // SMTP server configuration
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // Sender email credentials
        mailSender.setUsername(emailAddress);
        mailSender.setPassword(emailPassword); // Gmail App Password

        // Mail properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");

        return mailSender;
    }
    @Bean
    public TemplateEngine generateTemplateEngine() {

        ClassLoaderTemplateResolver templateResolver =
                new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

}
