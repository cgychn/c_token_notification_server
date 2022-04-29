package com.cloud.c_talk.notification_server.entity;

/**
 * 消息确认实体，由前台发送确认
 */
public class ConfirmMessage {

    private String mainUsername;

    private String whoseMessage;

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public String getWhoseMessage() {
        return whoseMessage;
    }

    public void setWhoseMessage(String whoseMessage) {
        this.whoseMessage = whoseMessage;
    }
}
