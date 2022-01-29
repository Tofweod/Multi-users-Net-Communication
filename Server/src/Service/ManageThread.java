package Service;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 管理和客户端通信的线程
 */
public class ManageThread {
  private static HashMap<String,ServerConnectedClientThread> hm = new HashMap<>();

  private static HashSet<String> onlineUsers = new HashSet<>();

  public static HashSet<String> getOnlineUsers() {
    return onlineUsers;
  }

  public static HashMap<String, ServerConnectedClientThread> getHm() {
    return hm;
  }

  public static void addThread(String UserId, ServerConnectedClientThread serverConnectedClientThread) {
    hm.put(UserId,serverConnectedClientThread);
  }

  public static ServerConnectedClientThread getThread(String UserId) {
      return hm.get(UserId);
  }



  /**
   * @Author
   * @PsnStudy
   * @Goal
   * 退出指定线程
   */
  public static void removeThread(String UserId) {
    hm.remove(UserId);
  }

  /**
   * @Author
   * @PsnStudy
   * @Goal
   * 返回在线用户列表
   */
  public static String getOnlineList() {
    StringBuilder sb = new StringBuilder();
    for (String key:
            hm.keySet()) {
      sb.append(key).append(" ");
    }
    return new String(sb);
  }

  /**
   * @Author
   * @PsnStudy
   * @Goal
   * 验证用户是否已经登录
   */
  public static boolean hasUser(String userId) {
    return onlineUsers.contains(userId);
  }
}
