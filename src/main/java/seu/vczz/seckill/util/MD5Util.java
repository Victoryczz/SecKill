package seu.vczz.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * CREATE by vczz on 2018/5/12
 * MD5工具类，使用两次MD5加密的方式
 */
public class MD5Util {


    private static final String salt = "vczz";

    /**
     * md5加密
     * @param src
     * @return
     */
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 第一次MD5加密，将用户的密码转换为表单中加过密的密码传递给服务器
     * @param pass
     * @return
     */
    public static String inputPassToFormPass(String pass){
        //先将原密码加盐
        String passAddSalt = ""+salt.charAt(1)+pass+salt.charAt(0)+salt.charAt(3);
        return md5(passAddSalt);
    }

    /**
     * 表单密码转换为数据库密码
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt){
        //表单传递过来的密码再次加盐，随机盐
        String formPassAddSalt = ""+salt.charAt(1)+formPass+salt.charAt(0)+salt.charAt(2);
        return md5(formPassAddSalt);
    }

    /**
     * 用户输入密码直接转换为数据库密码，其实是将两个步骤合一
     * @param inputPass
     * @param salt
     * @return
     */
    public static String inputPassToDBPass(String inputPass, String salt){
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass, salt);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("2333"));//da486fb27dc8321d300253dc3542098a;da486fb27dc8321d300253dc3542098a
        System.out.println(formPassToDBPass(inputPassToFormPass("2333"), "smj"));//571b83c3df0fd17f36900185d3f9fb64
        System.out.println(inputPassToDBPass("2333", "smj"));//571b83c3df0fd17f36900185d3f9fb64
    }


}






