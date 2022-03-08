package com.bookxchange.service;

import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.pojo.NotificationHelper;
import org.springframework.plugin.core.Plugin;

public interface NotificationProcessingPlugin extends Plugin<EmailTemplateType> {
    void sendMail(NotificationHelper userToBeNotifiedInfo);
}
