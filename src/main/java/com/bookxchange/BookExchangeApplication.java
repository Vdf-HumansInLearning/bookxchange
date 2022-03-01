package com.bookxchange;

import com.bookxchange.dto.BookDto;
import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.repositories.BookRepository;
import com.bookxchange.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class BookExchangeApplication extends SpringBootServletInitializer {


    NotificationService ns;
    BookRepository br;

    @Autowired
    public BookExchangeApplication(NotificationService ns, BookRepository br) {
        this.ns = ns;
        this.br = br;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookExchangeApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder){
        return builder.sources(BookExchangeApplication.class);
    }

    @Scheduled(fixedRateString = "${notification.check.every}")
    void notificationCronJob(){

      //  ns.checkForNotifications();


        Optional<BooksEntity> byId = br.findById("0-7475-3269-9");
        System.out.println(byId.get());

        System.out.println("Notification Cron Running... " + new Date());
    }


}
