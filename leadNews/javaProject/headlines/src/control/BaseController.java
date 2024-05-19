package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //响应MIME类型和乱码问题
        resp.setContentType("application/json");

        String requestURI = req.getRequestURI();
        String[] split = requestURI.split("/");
        String methodName = split[split.length - 1];

        //通过反射获得执行方法
        Class clazz = this.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            System.out.println("反射:+"+method);
            //设置方法可访问
            method.setAccessible(true);
            //通过反射执行代码
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
