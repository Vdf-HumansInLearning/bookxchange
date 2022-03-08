package com.bookxchange.service;

import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.pojo.NotificationHelper;
import org.springframework.stereotype.Component;

@Component
public class WarningNotificationService implements NotificationProcessingPlugin {
    @Override
    public void sendMail(NotificationHelper userToBeNotifiedInfo) {
        // warning implementation goes here
    }

    @Override
    public boolean supports(EmailTemplateType emailTemplateType) {
        return emailTemplateType.equals(EmailTemplateType.WARNING);
    }
}
