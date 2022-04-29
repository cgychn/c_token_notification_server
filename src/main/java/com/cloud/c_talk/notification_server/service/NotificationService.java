package com.cloud.c_talk.notification_server.service;

import com.cloud.c_talk.notification_server.dealer.MessageBoxDealer;
import com.cloud.c_talk.notification_server.entity.PackagedInteger;
import com.cloud.c_talk.notification_server.websocket.WebSocketEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final HashMap<String, HashMap<String, PackagedInteger>> unReadMessageCount = new HashMap<>(1024);

    /**
     * 发送通知
     * @param mainUsername
     * @param toUsername
     * @param msgString
     */
    public void notifyMessage(String mainUsername, String toUsername, String msgString) {
        try {
            // 通知自己
            WebSocketEventBus.notify(mainUsername, msgString);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            // 添加对方的未读消息
            MessageBoxDealer.addUnReadMessageCount(toUsername, mainUsername);
            // 通知对方
            WebSocketEventBus.notify(toUsername, msgString);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 群消息通知
     * @param senderUsername
     * @param toUsernames
     * @param msgString
     * @param groupAccount
     */
    public void notifyGroupMessage(String senderUsername, List<String> toUsernames, String msgString, String groupAccount) {
        try {
            // 通知自己
            WebSocketEventBus.notify(senderUsername, msgString);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        for (String toUsername : toUsernames) {
            try {
                MessageBoxDealer.addUnReadMessageCount(toUsername, groupAccount);
                WebSocketEventBus.notify(toUsername, msgString);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 获得未读消息数
     * @param mainUsername
     * @param toUsername
     * @return
     */
    public int getUnReadMessageCount (String mainUsername, String toUsername) {
        return MessageBoxDealer.getUnReadMessageCount(mainUsername, toUsername);
    }

    /**
     * 获取消息盒子
     * @param username
     * @return
     */
    public HashMap<String, PackagedInteger> getMessageBoxByMainUsername (String username) {
        return MessageBoxDealer.getMessageBoxByMainUsername(username);
    }

}
