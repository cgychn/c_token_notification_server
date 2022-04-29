package com.cloud.c_talk.notification_server.websocket;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.notification_server.dealer.MessageBoxDealer;
import com.cloud.c_talk.notification_server.entity.ConfirmMessage;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName){
        WebSocketEventBus.subscribe(userName, session);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName){
        WebSocketEventBus.unSubscribe(userName);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message, Session session) throws IOException{
        if (message.startsWith("ConfirmMessage:")) {
            message = message.replaceFirst("ConfirmMessage:", "");
            ConfirmMessage confirmMessage = JSONObject.parseObject(message, ConfirmMessage.class);
            MessageBoxDealer.resetUnReadMessageCount(confirmMessage.getMainUsername(), confirmMessage.getWhoseMessage());
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){

    }

}
