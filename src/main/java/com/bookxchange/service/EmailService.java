package com.bookxchange.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Builder
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(String toEmail,
                         String subject,
                         String body) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
     try {
         helper.setText(body, true); // Use this or above line.
         helper.setTo(toEmail);
         helper.setSubject(subject);
         helper.setFrom("${spring.mail.username}");
         mailSender.send(mimeMessage);
     } catch (MessagingException myexception){
         System.out.println("Error sending email");
     }

    }
}
