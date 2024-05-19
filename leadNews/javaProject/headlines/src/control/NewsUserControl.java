package control;

import common.Result;
import common.ResultCodeEnum;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.NewsUser;
import pojo.Vo.HeadlineDetailVo;
import service.NewsHeadlineService;
import service.NewsUserService;
import service.impl.NewsUserServiceImpl;
import util.JwtHelper;
import util.MD5Util;
import util.WEBUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class NewsUserControl extends BaseController{
    private NewsUserService newsUserService= new NewsUserServiceImpl();



    /**
     * 头条发布或删除前登录校验
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //注意 请求参数 getParameter
        //请求头 getHeader
        String token = req.getHeader("token");
        Result result = null;
        if(!JwtHelper.isExpiration(token)){
            result = Result.ok(null);
        }else{
            result = Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        WEBUtil.writeJson(resp,result);

    }

    /**
     * 处理注册信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser register = WEBUtil.readJson(req, NewsUser.class);
        //校验用户名是否被占用
        NewsUser newsUser = newsUserService.findByUserName(register.getUsername());
        Result result = null;
        if(newsUser == null){
            //用户名未被占用
            newsUserService.registUser(register);
            result = Result.ok(null);

        }else{
            result = Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WEBUtil.writeJson(resp, result);


    }

    /**
     * 注册时查询用户名是否可用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        NewsUser newsUser = newsUserService.findByUserName(username);
        Result result=null;
        if (null == newsUser){
            result=Result.ok(null);
        }else{
            result=Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WEBUtil.writeJson(resp,result);

    }

    /**
     * 登录验证
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收json数据
        NewsUser newsUser = WEBUtil.readJson(req, NewsUser.class);
        Result result = null;

        NewsUser loginUser = newsUserService.findByUserName(newsUser.getUsername());
        //判断用户名
        if(loginUser != null) {
            //判断密码
            if(loginUser.getUserPwd().equals(MD5Util.encrypt(newsUser.getUserPwd()))) {
                //密码正确
                Map<String,Object> data = new HashMap<String,Object>();
                //生成token
                String token = JwtHelper.createToken(loginUser.getUid().longValue());
                //封装结果
                data.put("token", token);
                result = Result.ok(data);
            }else{
                //密码错误
                result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
            }
        }else{
            // 封装用户名错误结果
            result=Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //响应结果
        WEBUtil.writeJson(resp,result);
    }

    /**
     * 根据token获取用户完整的信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String token = req.getParameter("token");
        //从请求头中获取用户信息，而不是请求体
        String token = req.getHeader("token");
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        if(null != token){
            if(!JwtHelper.isExpiration(token)){
                Integer uid = JwtHelper.getUserId(token).intValue();
                //token没过期且不为空
                NewsUser newsUser = newsUserService.findByUserId(uid);
                //获取用户信息
                newsUser.setUserPwd("");
                Map<String,NewsUser> data = new HashMap<>();
                data.put("loginUser",newsUser);
                result = Result.ok(data);
            }
        }
        WEBUtil.writeJson(resp,result);

    }


}
