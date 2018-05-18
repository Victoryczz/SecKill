package seu.vczz.seckill.access;

import seu.vczz.seckill.domain.User;

/**
 * CREATE by vczz on 2018/5/18
 * 存在用户信息
 */
public class UserContext {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

}
