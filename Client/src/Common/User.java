package Common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 */
public class User implements Serializable {
    // 增强兼容性

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户属性
     */
    private String userId;
    private String passWord;

    public User() {
    }

    public User(String userId, String passWord) {
        this.userId = userId;
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }



}
