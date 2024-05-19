package service.impl;

import dao.NewsHeadLineDao;
import dao.impl.NewsHeadlineDaoImpl;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;
import service.NewsHeadlineService;
import pojo.Vo.HeadlinePageVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private NewsHeadLineDao newsHeadLineDao =new NewsHeadlineDaoImpl();

    public Map<String, Object> findPage(HeadlineQueryVo headLineQueryVo) {
        // 准备一个map,用于装分页的五项数据
        Map<String,Object> pageInfo =new HashMap<>();
        // 分页查询本页数据
        List<HeadlinePageVo> pageData =newsHeadLineDao.findPageList(headLineQueryVo);
        // 分页查询满足记录的总数据量
        int totalSize = newsHeadLineDao.findPageCount(headLineQueryVo);
        // 页大小
        int pageSize = headLineQueryVo.getPageSize();
        // 总页码数
        int totalPage=totalSize%pageSize == 0 ?  totalSize/pageSize  : totalSize/pageSize+1;
        // 当前页码数
        int pageNum= headLineQueryVo.getPageNum();
        pageInfo.put("pageData",pageData);
        pageInfo.put("pageNum",pageNum);
        pageInfo.put("pageSize",pageSize);
        pageInfo.put("totalPage",totalPage);
        pageInfo.put("totalSize",totalSize);


        return pageInfo;
    }

    public HeadlineDetailVo findHeadlineDetail(Integer hid) {
        //新闻浏览量+1
        newsHeadLineDao.increasePageViews(hid);
        //查询新闻详细信息
        return newsHeadLineDao.findHeadlineDetail(hid);
    }


    public int addNewsHeadline(HeadlineDetailVo headlineDetailVo) {
        return newsHeadLineDao.addNewsHeadline(headlineDetailVo);
    }


    public HeadlineDetailVo getByHid(Integer hid) {
        return newsHeadLineDao.getByHid(hid);
    }


    public int updateHeadline(HeadlineDetailVo headlineDetailVo) {
        return newsHeadLineDao.updateHeadline(headlineDetailVo);
    }

    @Override
    public int removeByHid(Integer hid) {
        return newsHeadLineDao.removeByHid(hid);
    }

}
