package com.bookxchange.controller;

import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService ns;

    @Autowired
    public NotificationController(NotificationService ns) {
        this.ns = ns;
    }

    @PostMapping("/")
    public ResponseEntity<NotificationsEntity> addNotification(@RequestBody String marketBookId, String memberId){


        return new ResponseEntity<>(ns.addNotification(marketBookId, memberId), HttpStatus.CREATED);
    }

}

