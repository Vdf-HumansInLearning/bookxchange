package com.bookxchange.service;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.NotificationEntity;
import com.bookxchange.pojo.NotificationHelper;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final BookMarketRepository bmr;

    @Autowired
    public NotificationService(EmailService emailService, NotificationRepository notificationRepository, BookMarketRepository bmr) {
        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
        this.bmr = bmr;
    }




    public void checkForNotifications() {
        List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
        emailToNotify.forEach(customer -> emailService.sendMail(customer.getEmailAddress(), "subj", "hi," + customer.getTitle() + " is avaialable"));
        // TODO: get template instead of hardcoded email content, update sent = 1 if mail sent successfully
    }

    public void addNotification (String marketBookId, String memberId){
        // check if already available

        BookMarketEntity bookMarketEntity = bmr.findById(marketBookId).get();
        String status = bookMarketEntity.getBookStatus();
        if(status.equals("AVAILABLE") || status.equals("SOLD")){
            // TODO: create custom exception instead of sout, check if member passed in param exists, check so it doesn't notify itself, check if duplicate notification
            System.out.println("Book is already available or has been SOLD " + status);
        } else {
            NotificationEntity notification = new NotificationEntity();
            notification.setMemberId(memberId);
            notification.setBookId(marketBookId);
            notification.setType("AVAILABILITY"); //TODO: set as enum
            notificationRepository.save(notification);

        }
    }
}
