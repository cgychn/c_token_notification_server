package com.cloud.c_talk.notification_server.websocket;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class WebSocketEventBus {

    private static final HashMap<String, Session> webSocketEventSessions = new HashMap<>();

    public static void subscribe (String sid, Session session) {
        webSocketEventSessions.put(sid, session);
    }

    public static void notify (String sid, String message) throws IOException {
        if (!webSocketEventSessions.containsKey(sid)) {
            return;
        }
        webSocketEventSessions
                .get(sid)
                .getBasicRemote().sendText(message);
    }

    public static void unSubscribe(String sid) {
        webSocketEventSessions.remove(sid);
    }
}
