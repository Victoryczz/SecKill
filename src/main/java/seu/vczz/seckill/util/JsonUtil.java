package seu.vczz.seckill.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;


/**
 * CREATE by vczz on 2018/5/11
 * json工具类，用来javaBean和string类型之间的转换
 */
public class JsonUtil {

    /**
     * 对象序列化为json
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String objToString(T value){
        if (value == null){
            return null;
        }
        Class clazz = value.getClass();
        //先用这几个简单的判断
        //如果是整数类型
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class) {//string类型
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {//long类型
            return ""+value;
        }else {
            //序列化为字符串
            return JSON.toJSONString(value);
        }
    }

    /**
     * 反序列化为javaBean
     * @param value json字符串
     * @param clazz javaBean
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T stringToObj(String value, Class<T> clazz){
        if (StringUtils.isEmpty(value) || clazz == null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(value);
        }else if(clazz == String.class) {
            return (T)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(value);
        }else {
            //反序列化为对象
            return JSON.toJavaObject(JSON.parseObject(value), clazz);
        }
    }

}
