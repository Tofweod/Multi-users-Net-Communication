package Service;

import Common.File;
import Common.MessageType;
import Utils.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 */
public class FileClientService {

    // 发送文件
    public void sendFile(String sender,String srcPath, String getter,String destPath) {
        FileInputStream fileInputStream = null;

        File file = new File();
        file.setMessageType(MessageType.MESSAGE_FILE_MES);
        file.setSender(sender);
        file.setGetter(getter);
        file.setSrc(srcPath);
        file.setDest(destPath);
        file.setSendTime(new Date().toString());

        try {
            System.out.println("你向" + file.getGetter() + "发送了文件:"  + file.getSrc());
            fileInputStream = new FileInputStream(srcPath);
            byte[] buf = StreamUtils.inputStreamToByteArray(fileInputStream);
            file.setFileBytes(buf);
            file.setFileLen((int) new java.io.File(srcPath).length()); // 设置文件大小
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(sender).getSocket().getOutputStream());
            oos.writeObject(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
