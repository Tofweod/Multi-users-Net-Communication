package Common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal 表示客户段和服务端通信时的消息对象
 */
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息属性
     */
    private String sender;
    private String getter;
    private String content;
    private String sendTime;

    /**
     * 信息类型[可以在接口中定义消息类型]
     */
    private String messageType;


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
