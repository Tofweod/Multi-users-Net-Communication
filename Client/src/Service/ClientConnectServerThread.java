package Service;


import Common.File;
import Common.Message;
import Common.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 客户端连接服务端线程
 * 该线程应该持有一个socket
 */
public class ClientConnectServerThread extends Thread{

    private boolean loop = true;

    private final Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * 线程一直在后台和服务端通信，循环
     */
    @Override
    public void run() {

        while (loop) {
            System.out.println("客户端线程等待消息。。。");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 如果没有数据发送，线程将阻塞在此
                Message message = (Message) ois.readObject();


                // 判断message类型，做相应业务处理
                if (message.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_LIST)) {
                    // 取出在线列表信息，并显示
                    // 规定服务端返回数据形式：用户名1 用户名2 用户名3。。。
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=======当前在线用户列表如下=======");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户" + onlineUsers[i]);
                    }
                }
                else if (message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println(message.getContent());
                }
                else if (message.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println(message.getContent());
                }
                else if (message.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                    File fileMessage = (File) message;
                    System.out.println(fileMessage.getSender() + "向你发送了文件在：" + fileMessage.getDest());
                    FileOutputStream fos = new FileOutputStream(fileMessage.getDest());
                    fos.write(fileMessage.getFileBytes());
                    fos.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
