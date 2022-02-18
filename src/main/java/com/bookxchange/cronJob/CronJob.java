package com.bookxchange.cronJob;

import com.bookxchange.service.EmailService;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CronJob extends TimerTask {
    private String name;
    private int secondsTimer;
    private EmailService em;

    public CronJob(String name, int secondsTimer, EmailService em) {
        this.name = name;
        this.secondsTimer = secondsTimer * 1000;
        this.em = em;
    }

    @Override
    public void run() {
        em.sendEmail();
        System.out.println(Thread.currentThread().getName() + " " + name + " the task has executed successfully " + new Date());

    }

    public void scheduleJob() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(this, 0, secondsTimer);
    }


}
