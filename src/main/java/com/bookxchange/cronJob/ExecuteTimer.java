package com.bookxchange.cronJob;

import com.bookxchange.service.EmailService;

public class ExecuteTimer {

    public static void main(String[] args) {

        EmailService en =
                new EmailService.EmailBuilder()
                        .sendingTo("antoniu.tudor@vodafone.com")
                        .withSubject("cf?")
                        .withMessageBody("cmz?")
                        .build();

        CronJob cj = new CronJob("Email", 20, en);
        cj.scheduleJob();
    }
}
