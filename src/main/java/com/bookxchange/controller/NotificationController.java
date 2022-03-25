package com.bookxchange.controller;

import com.bookxchange.dto.NotificationsDTO;
import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("")
    public ResponseEntity<NotificationsEntity> addNotification(@RequestBody NotificationsDTO notificationsDTO){

        return new ResponseEntity<>(notificationService.addNotification(notificationsDTO.getMarketBookUuid(), notificationsDTO.getMemberUuid()), HttpStatus.CREATED);
    }

}

