package com.bookxchange.scheduler;

import com.bookxchange.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
@Configuration
public class NotificationScheduler {

    private final NotificationService ns;

    @Autowired
    public NotificationScheduler(NotificationService ns) {
        this.ns = ns;
    }

    @Scheduled(fixedRateString = "${notification.check.every}")
    void notificationCronJob() {

        ns.checkForNotifications();

        System.out.println("Notification Cron Running... " + new Date());
    }
}
