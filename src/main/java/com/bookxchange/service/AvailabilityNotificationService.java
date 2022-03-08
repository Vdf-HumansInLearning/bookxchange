package com.bookxchange.service;

import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AvailabilityNotificationService implements NotificationProcessingPlugin {

    final EmailService emailService;
    final NotificationRepository notificationRepository;

    @Autowired
    public AvailabilityNotificationService(EmailService emailService, NotificationRepository notificationRepository) {
        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public void sendMail(NotificationHelper userToBeNotifiedInfo) {
        String body = String.format(userToBeNotifiedInfo.getContent_Body(), userToBeNotifiedInfo.getUsername(), userToBeNotifiedInfo.getTitle());
        emailService.sendMail(userToBeNotifiedInfo.getEmail_Address(), userToBeNotifiedInfo.getSubject(), body);
        notificationRepository.updateToSent(userToBeNotifiedInfo.getNotid());
    }

    @Override
    public boolean supports(EmailTemplateType emailTemplateType) {
        return emailTemplateType.equals(EmailTemplateType.AVAILABILITY);
    }
}
