package com.lister.employeeonboarding.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
/** Provides services for sending mails
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * @param toEmail- to the message to the corresponding user
     * @param status- status of the employee
     * @param rejectReason- reason for rejection if any
     * @param uuid- unique id to track the logging
     */
    public void sendSimpleEmail(String toEmail, String status, String rejectReason, UUID uuid) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "";
        String subject = "";
        if (status.equalsIgnoreCase("Notify")) {
            subject = "Regarding Form Completion";
            body = "Please Complete The Form at the earliest possible";
        }
        if (status.equalsIgnoreCase("COMPLETED")) {
            subject = "Regarding Form Approval";
            body = "Thank You for Filling out the details.If you have any queries,Please reach out to HRD";
        }
        if (status.equalsIgnoreCase("REJECTED")) {
            subject = "Your Details has been rejected";
            body = "The details which you filled have been rejected for the following reasons: " + rejectReason;
        }
        message.setFrom("indraneel316@gmail.com");
        message.setTo("indraneel316@gmail.com");
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        log.info("Mail Sent {} ",uuid);


    }
}
