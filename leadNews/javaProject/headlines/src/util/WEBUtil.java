package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * JSON转换的WEBUtil工具类
 */
public class WEBUtil {
    private static ObjectMapper objectMapper ;
    //初始化ObjectMapper
    static{
        objectMapper = new ObjectMapper();
        //设置JSON和Object转换时间格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    //从请求中获取JSON串并转换为Object
    public static <T> T readJson(HttpServletRequest request,Class<T> clazz){
        T t = null;
        BufferedReader reader = null;
        try{
            //获取一个从请求体中读取字符的字符输入流(读取JSON串)
            reader = request.getReader();
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
            //readValue 方法是 ObjectMapper 类的一个方法，用于将 JSON 数据反序列化为 Java 对象
            t = objectMapper.readValue(buffer.toString(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    //将Result对象转换为JSON串并放入响应对象
    public static void writeJson(HttpServletResponse response, Result result){
        response.setContentType("application/json;charset=utf-8");
        try {
            //writeValueAsString 将 result 对象序列化为一个 JSON 格式的字符串
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
