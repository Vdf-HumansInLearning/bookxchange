package com.bookxchange.service;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Builder
public class EmailService {

  private JavaMailSender mailSender;


  @Autowired
  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendMail(String toEmail,
                       String subject,
                       String body){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("${spring.mail.username}");
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);


  }
}
