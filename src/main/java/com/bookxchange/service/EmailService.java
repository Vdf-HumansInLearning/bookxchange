package com.bookxchange.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private String to;
    private String subject;
    private String emailBody;

    public EmailService(EmailBuilder emailBuilder) {
        this.to = emailBuilder.to;
        this.subject = emailBuilder.subject;
        this.emailBody = emailBuilder.messageBody;
    }

    public static class EmailBuilder {
        private String to;
        private String subject;
        private String messageBody;

        public EmailBuilder sendingTo(String to){
            this.to = to;
            return this;
        }
        public EmailBuilder withSubject(String subject){
            this.subject = subject;
            return this;
        }
        public EmailBuilder withMessageBody(String messageBody){
            this.messageBody = messageBody;
            return this;
        }
        public EmailService build(){
            return new EmailService(this);
        }
    }



    public void sendEmail () {

        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("mafiacartilor@gmail.com", "umudnbmzyvyarzla");

            }

        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mafiacartilor1@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(emailBody);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
