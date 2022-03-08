package com.bookxchange.service;

import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnablePluginRegistries(NotificationProcessingPlugin.class)
public class NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final BookMarketRepository bmr;
    private final PluginRegistry<NotificationProcessingPlugin, EmailTemplateType> pluginRegistry;

    @Autowired
    public NotificationService(EmailService emailService, NotificationRepository notificationRepository, BookMarketRepository bmr, PluginRegistry<NotificationProcessingPlugin, EmailTemplateType> pluginRegistry) {
        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
        this.bmr = bmr;
        this.pluginRegistry = pluginRegistry;
    }


    public void checkForNotifications() {


        try {
            List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
            System.out.println(emailToNotify.isEmpty());
            if(!emailToNotify.isEmpty()){
                emailToNotify.forEach(customer -> {
                    NotificationProcessingPlugin notificationPlugin = pluginRegistry.getPluginFor(customer.getTemplate_Name());
                    notificationPlugin.sendMail(customer);
                });
            }
            // TODO: get template instead of hardcoded email content, update sent = 1 if mail sent successfully
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tre facut mailu sa mearga");
        }
    }

    public void addNotification(String marketBookId, String memberId) {
        // check if already available

        BookMarketEntity bookMarketEntity = bmr.findById(marketBookId).get();
        String status = bookMarketEntity.getBookStatus();
        if (status.equals("AVAILABLE") || status.equals("SOLD")) {
            // TODO: create custom exception instead of sout, check if member passed in param exists, check so it doesn't notify itself, check if duplicate notification
            System.out.println("Book is already available or has been SOLD " + status);
        } else {
            NotificationsEntity notification = new NotificationsEntity();
            notification.setMemberId(memberId);
            notification.setMarketBookId(marketBookId);
            notification.setType("AVAILABILITY"); //TODO: set as enum
            notificationRepository.save(notification);

        }
    }
}
