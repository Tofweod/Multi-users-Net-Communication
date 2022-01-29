package Service;

import java.util.HashMap;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 管理客户端连接服务器端线程的类
 */
public class ManageThread {
  /**
   * key是UserId,value是线程
   */
  private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

  public static void addThread(String UserId, ClientConnectServerThread clientConnectServerThread) {
    hm.put(UserId,clientConnectServerThread);
  }

  public static ClientConnectServerThread getThread(String UserId) {
     return hm.get(UserId);
  }
}
