package com.bookxchange.service;

import com.bookxchange.customExceptions.NotificationException;
import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.NotificationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnablePluginRegistries(NotificationProcessingPlugin.class)
public class NotificationService {

    private static final Log LOG = LogFactory.getLog(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final BookMarketRepository bmr;
    private final PluginRegistry<NotificationProcessingPlugin, EmailTemplateType> pluginRegistry;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, BookMarketRepository bmr, PluginRegistry<NotificationProcessingPlugin, EmailTemplateType> pluginRegistry) {
        this.notificationRepository = notificationRepository;
        this.bmr = bmr;
        this.pluginRegistry = pluginRegistry;
    }


    public void checkForNotifications() {
        try {
            List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
            if(!emailToNotify.isEmpty()){
                emailToNotify.forEach(customer -> {
                    NotificationProcessingPlugin notificationPlugin = pluginRegistry.getPluginFor(customer.getTemplate_Name());
                    notificationPlugin.sendMail(customer);
                    LOG.info(String.format("E-mail sent to %s", customer.getEmail_Address()));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.fatal("Notification fatal error.");
        }
    }

    public NotificationsEntity addNotification(String marketBookId, String memberId) {
        // check if already available

        Optional<BookMarketEntity> bookMarket = bmr.findById(marketBookId);

        if(memberId.equals(bookMarket.get().getUserId())){
            throw new NotificationException("User can't notify for his own book.");
        }


        BookMarketEntity bookMarketEntity = bookMarket.get();
        String status = bookMarketEntity.getBookStatus();
        if (status.equals("AVAILABLE") || status.equals("SOLD")) { //todo use enums
            throw new NotificationException(String.format("Book is already %s"), status);
        } else {
            NotificationsEntity notification = new NotificationsEntity();
            notification.setMemberId(memberId);
            notification.setMarketBookId(marketBookId);
            notification.setTemplateType("1");
           return notificationRepository.save(notification);
        }

    }
}
