package Service;

import Common.File;
import Common.Message;
import Common.MessageType;
import Common.User;
import Utils.StreamUtils;


import java.io.*;
import java.util.Date;


/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 该类的对象提供消息相关方法
 */
public class MessageClientService {

    private User user = new User();

    public MessageClientService(String userId) {
        user.setUserId(userId);
    }

    /**
     * @Author
     * @PsnStudy
     * @Goal
     * 发送私聊
     */
    public void sendPrivateInfo(String getter, String content) {
        // 设置发送消息类型
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setSender(user.getUserId());
        message.setGetter(getter);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("你对" + getter + "说：" + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfoToAll(String content) {
        // 设置发送消息类型
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(user.getUserId());
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("你对大家说：" + content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
