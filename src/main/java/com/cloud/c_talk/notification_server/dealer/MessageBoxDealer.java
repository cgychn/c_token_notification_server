package com.cloud.c_talk.notification_server.dealer;

import com.cloud.c_talk.notification_server.entity.PackagedInteger;

import java.util.HashMap;

public class MessageBoxDealer {

    private static final HashMap<String, HashMap<String, PackagedInteger>> unReadMessageCount = new HashMap<>(1024);

    /**
     * 获取某个账号的消息盒子
     * @param username
     * @return
     */
    public static HashMap<String, PackagedInteger> getMessageBoxByMainUsername(String username) {
        return unReadMessageCount.get(username);
    }

    /**
     * 增加未读数量
     * @param mainUsername
     * @param to
     */
    public static void addUnReadMessageCount (String mainUsername, String to) {
        if (unReadMessageCount.containsKey(mainUsername)) {
            doAdd(mainUsername, to);
        }
        else {
            synchronized (unReadMessageCount) {
                if (!unReadMessageCount.containsKey(mainUsername)) {
                    unReadMessageCount.put(mainUsername, new HashMap<>(130));
                    unReadMessageCount.get(mainUsername).put(to, new PackagedInteger());
                    unReadMessageCount.get(mainUsername).get(to).add();
                } else {
                    doAdd(mainUsername, to);
                }
            }
        }
    }

    /**
     * 操作
     * @param mainUsername
     * @param to
     */
    private static void doAdd (String mainUsername, String to) {
        if (unReadMessageCount.get(mainUsername).containsKey(to)) {
            unReadMessageCount.get(mainUsername).get(to).add();
        } else {
            synchronized (unReadMessageCount.get(mainUsername)) {
                unReadMessageCount.get(mainUsername).put(to, new PackagedInteger());
                unReadMessageCount.get(mainUsername).get(to).add();
            }
        }
    }

    /**
     * 重置未读数量
     * @param mainUsername
     * @param to
     */
    public static void resetUnReadMessageCount (String mainUsername, String to) {
        if (unReadMessageCount.containsKey(mainUsername) && unReadMessageCount.get(mainUsername).containsKey(to)) {
            unReadMessageCount.get(mainUsername).get(to).reset();
        }
    }

    /**
     * 获取未读数量
     * @param mainUsername
     * @param to
     * @return
     */
    public static int getUnReadMessageCount (String mainUsername, String to) {
        if (unReadMessageCount.containsKey(mainUsername) && unReadMessageCount.get(mainUsername).containsKey(to)) {
            return unReadMessageCount.get(mainUsername).get(to).getI();
        } else {
            return 0;
        }
    }


}
