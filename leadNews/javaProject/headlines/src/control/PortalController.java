package control;

import common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.NewsType;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;
import service.NewsHeadlineService;
import service.NewsTypeService;
import service.impl.NewsHeadlineServiceImpl;
import service.impl.NewsTypeServiceImpl;
import util.WEBUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//门户类,未登录展示页
@WebServlet("/portal/*")
public class PortalController extends BaseController {
    NewsTypeService newsTypeService = new NewsTypeServiceImpl();
    NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();

    protected void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        Result result = null;
        HeadlineDetailVo headline = newsHeadlineService.findHeadlineDetail(hid);
        Map<String, Object> headlineMap = new HashMap<>();
        headlineMap.put("headline", headline);
        result = Result.ok(headlineMap);
        WEBUtil.writeJson(resp, result);
    }


    /**
     * 分页带条件查询所有头条
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HeadlineQueryVo headLineQueryVo = WEBUtil.readJson(req, HeadlineQueryVo.class);

        Map<String,Object> pageInfo = newsHeadlineService.findPage(headLineQueryVo);
        Map<String,Object> pageInfoMap = new HashMap<String,Object>();
        pageInfoMap.put("pageInfo", pageInfo);
        WEBUtil.writeJson(resp,Result.ok(pageInfoMap));

    }

    /**
     * 获取新闻类型
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NewsType> newsTypeList = newsTypeService.findAll();
        WEBUtil.writeJson(resp, Result.ok(newsTypeList));
    }


}
