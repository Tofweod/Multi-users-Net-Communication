package Common;

import Service.ManageThread;
import Service.ServerConnectedClientThread;
import Service.UsersCollection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 服务端，在9999端口监听，等待客户端连接，并保持通讯
 */
public class Frame {
    private ServerSocket serverSocket = null;

    private ConcurrentHashMap<String,User> validUsers = UsersCollection.getValidUsers();

    public static void main(String[] args) {
        new Frame().launch();
    }

    public void launch() {

        System.out.println("服务端在9999端口监听。。。");
        // 端口可以写在配置文件中
        try {
            serverSocket = new ServerSocket(9999);

            // 当和某个客户端连接后，会继续监听,否则阻塞在accept()
            while (true) {
                Socket socket = serverSocket.accept();

                // 得到socket关联的对象的输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();

                // 创建Message对象，准备回复客户端
                Message mg = new Message();

                // 回复Message
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                //检验user是否合法
                if (checkUser(user.getUserId(), user.getPassWord())) {
                    if (!ManageThread.hasUser(user.getUserId())) {
                        // 添加用户到已登录集合
                        ManageThread.getOnlineUsers().add(user.getUserId());

                        // 合法用户
                        mg.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEEDED);

                        // 将message回复给客户端
                        oos.writeObject(mg);

                        // 创建一个线程和客户端保持通信，该线程需要持有socket对象
                        ServerConnectedClientThread scct = new ServerConnectedClientThread(socket, user.getUserId());
                        scct.start();

                        // 把该线程对象放入到集合中进行管理
                        ManageThread.addThread(user.getUserId(), scct);
                    }
                    else {
                        System.out.println("用户id=" + user.getUserId() + "已经登录");
                        mg.setMessageType(MessageType.MESSAGE_LOGIN_FAILED);
                        oos.writeObject(mg);

                        //关闭失败
                        socket.close();
                    }
                }
                else {
                    // 登录失败
                    System.out.println("用户id=" + user.getUserId()+ "\t用户密码=" + user.getPassWord() + "验证失败" );
                    mg.setMessageType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(mg);

                    //关闭失败
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            // 退出while循环说明服务端不再监听，关闭ServerSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * @Author
     * @PsnStudy
     * @Goal
     * 验证用户是否有效
     */
    public boolean checkUser(String UserId,String pwd) {
        User user = validUsers.get(UserId);

        // userId不存在
        if (user == null) {
            return false;
        }

        // 密码错误
        if (!(user.getPassWord().equals(pwd))) {
            return false;
        }

        return true;
    }



}
