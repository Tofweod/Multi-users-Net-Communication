package Service;

import Common.File;
import Common.Message;
import Common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 该类的对象和某个客户端保持通信，持有socket
 */
public class ServerConnectedClientThread extends Thread{

    private Socket socket;

    private String UserId;

    public ServerConnectedClientThread(Socket socket, String UserId) {
        this.socket = socket;
        this.UserId = UserId;
    }

    @Override
    public void run() {
        // 线程处于run状态，可以发送接收消息
        while (true) {
            System.out.println("服务端和客户端" + UserId +" 保持通讯。。。");
            try {


                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message mg = (Message)ois.readObject();

                // 返回message
                Message retMessage = new Message();

                // 退出进程
                if (mg.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println("用户" + UserId + "已经退出服务端");
                    ManageThread.removeThread(UserId); // remove线程
                    ManageThread.getOnlineUsers().remove(UserId); // remove在线用户集合
                    socket.close();
                    break;
                }



                if (mg.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_LIST)) {
                    System.out.println("用户" + mg.getSender() + "要求在线用户列表");

                    // 客户要求在线用户列表
                    retMessage.setMessageType(MessageType.MESSAGE_RET_ONLINE_LIST);
                    retMessage.setContent(ManageThread.getOnlineList());
                    retMessage.setGetter(mg.getSender());

                    sendRetMessage(retMessage);
                }
                // 私聊
                else if (mg.getMessageType().equals(MessageType.MESSAGE_COMM_MES)){
                    if (!mg.getSender().equals(mg.getGetter()) && ManageThread.hasUser(mg.getGetter())) {
                        System.out.println(mg.getSender() + "向" + mg.getGetter() + "私发消息成功");

                        // 获取getter线程对应socket的inputStream
                        ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(mg.getGetter()).socket.getOutputStream());
                        mg.setContent(mg.getSender() + "对你说" +
                                mg.getContent());
                        oos.writeObject(mg);
                    }
                    else {
                        System.out.println(mg.getSender() + "向" + mg.getGetter() + "私发消息失败");

                        retMessage.setContent(mg.getGetter() + "未登录，发送失败");

                        sendRetMessage(retMessage);
                    }
                }
                // 群发
                else if (mg.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println(mg.getSender() + "向群发消息成功");

                    mg.setContent(mg.getSender() + "对大家说：" + mg.getContent());
                    for (String userId:
                        ManageThread.getOnlineUsers()) {
                        if (!mg.getSender().equals(userId)) {
                            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(userId).socket.getOutputStream());
                            oos.writeObject(mg);
                        }
                    }
                }
                // 发文件
                else if (mg.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                    File file = (File)mg;
                    System.out.println(file.getSender() + "向" + file.getGetter() + "发送文件");
                    ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(file.getGetter()).socket.getOutputStream());
                    oos.writeObject(file);
                }




            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Author
     * @PsnStudy
     * @Goal
     * 返回retMessage
     */
    public void sendRetMessage(Message retMessage) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(retMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
