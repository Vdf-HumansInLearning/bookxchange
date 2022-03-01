package com.bookxchange.service;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.NotificationsEntity;
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
        try {
            List<NotificationHelper> emailToNotify = notificationRepository.getEmailToNotify();
            emailToNotify.forEach(customer -> emailService.sendMail(customer.getEmail_Address(), "subj", "hi," + customer.getTitle() + " is avaialable"));
            // TODO: get template instead of hardcoded email content, update sent = 1 if mail sent successfully
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tre facut mailu sa mearga");
        }
    }

    public void addNotification (String marketBookId, String memberId){
        // check if already available

        BookMarketEntity bookMarketEntity = bmr.findById(marketBookId).get();
        String status = bookMarketEntity.getBookStatus();
        if(status.equals("AVAILABLE") || status.equals("SOLD")){
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
