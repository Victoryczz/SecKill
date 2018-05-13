package seu.vczz.seckill.util;

import java.util.UUID;

/**
 * CREATE by vczz on 2018/5/13
 * UUID
 */
public class UUIDUtil {

    public static String uuid(){
        //原生的带横杠，去掉
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }

}
