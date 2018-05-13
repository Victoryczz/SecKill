package seu.vczz.seckill.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CREATE by vczz on 2018/5/12
 * 格式验证工具类
 */
public class ValidatorUtil {
    //使用正则表达式简单实现
    //如果是1开头，而且一共11位就代表是手机号
    public static final Pattern mobilePattern = Pattern.compile("1\\d{10}");
    /**
     * 判断是否是手机号
     * @param src
     * @return
     */
    public static boolean isMobile(String src){
        if (StringUtils.isEmpty(src)){
            return false;
        }
        Matcher mobileMatcher = mobilePattern.matcher(src);
        return mobileMatcher.matches();
    }
}
