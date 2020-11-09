package com.hometask.task_security.service;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.EmailRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailService {
    static Logger LOG = LoggerFactory.getLogger(EmailService.class);

    JavaMailSender emailSender;
    EmailRepo emailRepo;


    @Scheduled(fixedRate = 60000) //1 min
    public void sendSimpleEmail() throws MailException {
        List<Email> emailsNearDeliveryDate = emailRepo.findEmailsForSending();
        for (Email emails : emailsNearDeliveryDate) {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(emails.getRecipient());
            message.setSubject(emails.getSubject());
            message.setText(emails.getSubject());

            LOG.info("All information about your email {}:", emails.toString());
            this.emailSender.send(message);

            emailRepo.deleteById(emails.getId());
        }
    }
}