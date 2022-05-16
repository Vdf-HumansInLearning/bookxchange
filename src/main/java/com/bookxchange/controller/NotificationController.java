package com.bookxchange.controller;

import com.bookxchange.dto.NotificationDTO;
import com.bookxchange.model.NotificationEntity;
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

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("")
    public ResponseEntity<NotificationEntity> addNotification(@RequestBody NotificationDTO notificationDTO){

        return new ResponseEntity<>(notificationService.addNotification(notificationDTO.getMarketBookUuid(), notificationDTO.getMemberUuid()), HttpStatus.CREATED);
    }

}

