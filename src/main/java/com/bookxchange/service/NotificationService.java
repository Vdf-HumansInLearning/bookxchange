package com.bookxchange.service;

import com.bookxchange.customExceptions.NotificationException;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Log LOG = LogFactory.getLog(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final BookMarketRepository bmr;

    PluginService pluginService;

    public void checkForNotifications() {
        try {
            List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
            if (!emailToNotify.isEmpty()) {
                emailToNotify.forEach(customer -> {
                    NotificationProcessingPlugin notificationPlugin = pluginService.getPlugin(customer.getTemplate_Name());
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

        boolean isDuplicate = notificationRepository.existsNotificationsEntitiesByMarketBookIdAndMemberId(marketBookId, memberId);

        Optional<BookMarketEntity> bookMarket = bmr.findById(marketBookId);

        if (bookMarket.isPresent()) {
            BookMarketEntity bookMarketEntity = bookMarket.get();
            String status = bookMarketEntity.getBookStatus();
            if (status.equals(BookStatus.AVAILABLE.toString()) || status.equals(BookStatus.SOLD.toString())) {
                String format = String.format("Book is already '%s'", status);
                System.out.println(format);
                throw new NotificationException(format);
            } else if (!isDuplicate) {
                NotificationsEntity notification = new NotificationsEntity();
                notification.setMemberId(memberId);
                notification.setMarketBookId(marketBookId);
                notification.setTemplateType(1);
                notification.setSent((byte) 0);
                return notificationRepository.save(notification);
            } else if (isDuplicate) {
                throw new NotificationException("Duplicate Notification");
            }
        } else {
            throw new NotificationException("Empty BookMarket");
        }
        return null;
    }
}
