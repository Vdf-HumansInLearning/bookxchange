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

    private final NotificationService ns;

    @Autowired
    public NotificationController(NotificationService ns) {
        this.ns = ns;
    }

    @PostMapping("")
    public ResponseEntity<NotificationsEntity> addNotification(@RequestBody NotificationsDTO notificationsDTO){

        System.out.println("in controller" + notificationsDTO.getMarketBookUuid());
        System.out.println("in controller" + notificationsDTO.getMemberUuid());

        return new ResponseEntity<>(ns.addNotification(notificationsDTO.getMarketBookUuid(), notificationsDTO.getMemberUuid()), HttpStatus.CREATED);
    }

}

