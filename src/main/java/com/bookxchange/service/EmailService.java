package com.bookxchange.service;

import com.bookxchange.model.EmailEntity;
import com.bookxchange.repository.EmailsRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Builder
public class EmailService {

    private JavaMailSender mailSender;

   @Autowired
    private final EmailsRepository emailsRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, EmailsRepository emailsRepository) {
        this.mailSender = mailSender;
        this.emailsRepository = emailsRepository;
    }

    @Async
    public void sendMail(String toEmail,
                         String subject,
                         String body,
                         Integer memberId) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
     try {
         helper.setText(body, true); // Use this or above line.
         helper.setTo(toEmail);
         helper.setSubject(subject);
         helper.setFrom("${spring.mail.username}");
         mailSender.send(mimeMessage);

         EmailEntity emailsEntity = new EmailEntity();
         emailsEntity.setContent(body);
         emailsEntity.setStatus("SENT");
         emailsEntity.setMemberId(memberId);
         emailsEntity.setSentDate(Date.valueOf(LocalDate.now()));
         emailsRepository.save(emailsEntity);

     } catch (MessagingException myexception){
         System.out.println("Error sending email");
     }

    }
}
