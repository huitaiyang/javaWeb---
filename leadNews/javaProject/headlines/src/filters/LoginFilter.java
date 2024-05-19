package filters;

import common.Result;
import common.ResultCodeEnum;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.JwtHelper;
import util.WEBUtil;

import java.io.IOException;

/**
 * 登录校验过滤器
 */
@WebFilter(urlPatterns = {"/headline/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        boolean flag = false;
        if (token != null) {
            boolean expiration = JwtHelper.isExpiration(token);
            if(!expiration) {
                flag = true;
            }
        }
        if (flag) { //未过期,发行
            filterChain.doFilter(request, response);
        }else{ //过期
            WEBUtil.writeJson(response, Result.build(null, ResultCodeEnum.NOTLOGIN));
        }
    }
}
