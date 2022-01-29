package Service;

import Common.User;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Tofweod
 * @PsnStudy
 * @Goal
 * 模拟数据库
 */
public class UsersCollection {

    // ConcurrentHashMap可以并发处理集合，是线程安全的
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, User> getValidUsers() {
        return validUsers;
    }

    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("400",new User("400","123456"));
    }

}
