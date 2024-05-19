package control;

import common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Vo.HeadlineDetailVo;
import service.NewsHeadlineService;
import service.impl.NewsHeadlineServiceImpl;
import util.JwtHelper;
import util.WEBUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/headline/*")
public class NewsHeadlineControl extends BaseController {
    private NewsHeadlineService headlineService = new NewsHeadlineServiceImpl();

    /**
     * 删除头条
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        headlineService.removeByHid(hid);
        WEBUtil.writeJson(resp,Result.ok(null));
    }

    /**
     * 保存修改
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HeadlineDetailVo headlineDetailVo = WEBUtil.readJson(req,HeadlineDetailVo.class);
        headlineService.updateHeadline(headlineDetailVo);
        WEBUtil.writeJson(resp,Result.ok(null));


    }

    /**
     * 修改新闻时信息回显
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo headlineDetailVo = headlineService.getByHid(hid);
        Map<String, Object> headlineMap = new HashMap<>();
        headlineMap.put("headline", headlineDetailVo);
        WEBUtil.writeJson(resp,Result.ok(headlineMap));

    }

    /**
     * 提交发布头条
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        HeadlineDetailVo headlineDetailVo = WEBUtil.readJson(req, HeadlineDetailVo.class);
        headlineDetailVo.setPublisher(userId.intValue());
        headlineService.addNewsHeadline(headlineDetailVo);
        WEBUtil.writeJson(resp,Result.ok(null));

    }
}
