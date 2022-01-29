package Service;


import Common.Message;
import Common.MessageType;
import Common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 完成用户登录验证和用户注册等功能
 */
public class UserClientService {

  // 可能在其他地方使用user信息，因此做成属性
  private User user = new User();

  private Socket socket;

  /**
   * 根据UserId和pwd到服务器验证用户是否合法
   * @return boolean
   */
  public boolean checkUser(String UserId, String pwd) throws IOException, ClassNotFoundException {
    boolean res = false;

    // 创建用户
    user.setUserId(UserId);
    user.setPassWord(pwd);

    // 连接到服务端发送User对象
    socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    oos.writeObject(user);

    // 读取服务端发送的Message对象,用于检验用户是否合理
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
     Message mg = (Message) ois.readObject();

    if (mg.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEEDED)) {

      // 登录成功则启动一个和服务端保持通信的线程维护该socket
      ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
      ccst.start();

      // 将ccst加入HashMap中
      ManageThread.addThread(UserId,ccst);
      res = true;
    } else {
      // 关闭socket
      socket.close();
    }
    return res;
  }

  /**
   * @Author
   * @PsnStudy
   * @Goal
   * 获取在线用户列表方法
   */
  public void getOnlineList() {
    // 发送一个message，类型为MESSAGE_GET_ONLINE_LIST
    Message message = new Message();
    message.setMessageType(MessageType.MESSAGE_GET_ONLINE_LIST);
    message.setSender(user.getUserId());

    // 发送给服务端
    try {
      // 先得到当前线程的socket对应的ObjectOutputStream
      // ManageThread.getThread(user.getUserId()).getSocket().getOutputStream()
      ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(user.getUserId()).getSocket().getOutputStream());

      // 发送要求在线用户列表信息
      oos.writeObject(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @Author
   * @PsnStudy
   * @Goal
   * 客户端请求退出
   */
  public void Exit() {
    Message message = new Message();
    message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
    message.setSender(user.getUserId());

    // 发送
    try {
      ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(user.getUserId()).getSocket().getOutputStream());
      oos.writeObject(message);
      System.out.println(user.getUserId() + "退出系统");
      System.exit(0);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
