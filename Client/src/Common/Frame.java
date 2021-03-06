package Common;

import Service.FileClientService;
import Service.MessageClientService;
import Service.UserClientService;
import Utils.Utility;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal 客户端菜单节面
 */
public class Frame {

  public static void main(String[] args) {
    new Frame().launch();
    System.out.println("客户端退出系统");
  }

  private boolean loop = true;//控制是否现实菜单
  private String key = "";
  Scanner scanner = new Scanner(System.in);
  // 用于登录服务器，注册用户
  private UserClientService ucs = new UserClientService();

  public void launch() {
    while (loop) {
      System.out.println("===========欢迎登录网络通讯系统===========");
      System.out.println("\t\t 1 登录系统");
      System.out.println("\t\t 9 退出系统");

      key = scanner.next();

      switch (key) {
        case "1" :
          System.out.print("请输入用户号：");
          String userId = Utility.readString(10);
          System.out.print("请输入密 码：");
          String pwd = Utility.readString(10);
          // 到服务端验证该用户是否合法
          try {
            if (ucs.checkUser(userId,pwd)) {
              MessageClientService mcs = new MessageClientService(userId);
              System.out.println("===========欢迎用户" + userId + "登录成功============");
              while (loop) {
                System.out.println("=============网络通信系统二级菜单（用户"+ userId + "）=============");
                System.out.println("\t\t 1 显示在线用户列表");
                System.out.println("\t\t 2 群发消息");
                System.out.println("\t\t 3 私聊消息");
                System.out.println("\t\t 4 发送文件");
                System.out.println("\t\t 9 退出系统");
                System.out.println("请输入你的选择");

                key = scanner.next();

                switch (key) {
                  case "1":
                    ucs.getOnlineList();
                    break;
                  case "2":
                    System.out.print("请输入想说的话：");

                    String plcContent = Utility.readString(50);
                    mcs.sendInfoToAll(plcContent);
                    break;
                  case "3":
                    System.out.print("请输入想聊天的·用户号（在线）：");
                    String getter = Utility.readString(10);

                    System.out.print("请输入想说的话：");
                    String prvContent = Utility.readString(50);
                    mcs.sendPrivateInfo(getter,prvContent);
                    break;
                  case "4":
                    FileClientService fcs = new FileClientService();
                    System.out.print("请输入传输文件路径：");
                    String srcPath = Utility.readString(50);
                    System.out.print("请输入接收文件用户：");
                    String fileGetter = Utility.readString(10);
                    System.out.print("请输入发送文件到对方的路径：");
                    String destPath = Utility.readString(50);
                    fcs.sendFile(userId,srcPath,fileGetter,destPath);
                    break;
                  case "9":
                    ucs.Exit();
                    loop = false;
                    break;
                }
              }
            }
            else {
              System.out.println("=========登录服务器失败=========");
            }
          } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
          }
          break;
        case "9" :
          loop = false;
          break;
      }
    }
  }



}
