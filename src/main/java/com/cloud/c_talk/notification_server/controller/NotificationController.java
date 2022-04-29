package com.cloud.c_talk.notification_server.controller;

import com.cloud.c_talk.notification_server.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("notify/message")
    public Boolean notifyImMessage (String mainUsername, String toUsername, String msgString) {
        notificationService.notifyMessage(mainUsername, toUsername, msgString);
        return true;
    }

    @RequestMapping("notify/group/message")
    public Boolean notifyImGroupMessage (String senderUsername, List<String> groupUsers, String groupAccount, String msgString) {
        notificationService.notifyGroupMessage(senderUsername, groupUsers, groupAccount, msgString);
        return true;
    }

}
