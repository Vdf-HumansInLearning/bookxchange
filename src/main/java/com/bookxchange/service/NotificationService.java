package com.bookxchange.service;

import com.bookxchange.customExceptions.NotificationException;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.EmailTemplateType;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.EmailsEntity;
import com.bookxchange.model.NotificationEntity;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.EmailsRepository;
import com.bookxchange.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Log LOG = LogFactory.getLog(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final BookMarketRepository bmr;
    private final EmailService emailService;
    private final EmailsRepository emailsRepository;

    @Transactional
    public void checkForNotifications() {
        try {
            List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
            if (!emailToNotify.isEmpty()) {
                emailToNotify.stream().filter(customer -> customer.getTemplate_Name().equals(EmailTemplateType.AVAILABILITY)).forEach(this::sendMail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("Notification fatal error.");
        }
    }

    public NotificationEntity addNotification(String marketBookId, String memberId) {

        boolean isDuplicate = notificationRepository.existsNotificationsEntitiesByMarketBookUuidAndMemberUuid(marketBookId, memberId);

        Optional<BookMarketEntity> bookMarket = bmr.findByBookMarketUuid(marketBookId);
        NotificationEntity notification = new NotificationEntity();
        if (bookMarket.isPresent()) {
            BookMarketEntity bookMarketEntity = bookMarket.get();
            String status = bookMarketEntity.getBookStatus();
            if (!isDuplicate) {
                notification.setMemberUuid(memberId);
                notification.setMarketBookUuid(marketBookId);
                notification.setTemplateType(1);
                notification.setSent((byte) 0);
            } else if (status.equals(BookStatus.AVAILABLE.toString()) || status.equals(BookStatus.SOLD.toString())) {
                String format = String.format("Book is already '%s'", status);
                LOG.debug("format : " + format);
                throw new NotificationException(format);
            } else if (isDuplicate) {
                throw new NotificationException("Duplicate Notification");
            }
        } else {
            throw new NotificationException("Empty BookMarket");
        }
        return notificationRepository.save(notification);
    }


    private void sendMail(NotificationHelper userToBeNotifiedInfo) {
        EmailsEntity emailsEntity = new EmailsEntity();
        try {
            String body = String.format(userToBeNotifiedInfo.getContent_Body(), userToBeNotifiedInfo.getUsername(), userToBeNotifiedInfo.getTitle());
            emailService.sendMail(userToBeNotifiedInfo.getEmail_Address(), userToBeNotifiedInfo.getSubject(), body);
            notificationRepository.updateToSent(userToBeNotifiedInfo.getNotid());
            emailsEntity.setContent(body);
            emailsEntity.setSentDate(Date.valueOf(LocalDate.now()));
            emailsEntity.setMemberId(userToBeNotifiedInfo.getMember_User_Id());
            LOG.debug("emailEntity created : " + emailsEntity);
        } catch (Exception e) {
            emailsEntity.setStatus("ERROR");
            LOG.info("Error in sending e-mail.");
        }
        emailsRepository.save(emailsEntity);
    }
}
