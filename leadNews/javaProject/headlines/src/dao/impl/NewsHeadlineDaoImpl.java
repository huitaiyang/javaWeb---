package dao.impl;

import dao.BaseDao;
import dao.NewsHeadLineDao;
import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlinePageVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadLineDao {
    public List<HeadlinePageVo> findPageList(HeadlineQueryVo headLineQueryVo) {
        //拼接动态sql，拼接参数
        List<Object> args = new ArrayList<>();
        String sql = """
                select hid,title,type,page_views pageViews,
                TIMESTAMPDIFF(HOUR,create_time,NOW()) pastHours,publisher from news_headline
                 where is_deleted=0
                """;
        StringBuffer sqlBuffer = new StringBuffer(sql);
        String keywords = headLineQueryVo.getKeyWords();
        if(null != keywords && !"".equals(keywords)){
            sqlBuffer.append(" and title like ? ");
            args.add("%"+keywords+"%");
        }

        Integer type = headLineQueryVo.getType();
        if(null != type  && type != 0){
            sqlBuffer.append("and type  =  ? ");
            args.add(type);
        }

        //按时间升序，浏览量降序排序
        sqlBuffer.append("order by pastHours asc , page_views desc ");
        sqlBuffer.append("limit ?,? ");
        args.add((headLineQueryVo.getPageNum()-1)*headLineQueryVo.getPageSize());
        args.add(headLineQueryVo.getPageSize());

        //参数转数组
        Object[] argsArr = args.toArray();
        List<HeadlinePageVo> pageData = baseQuery(HeadlinePageVo.class, sqlBuffer.toString(), argsArr);

        return pageData;
    }


    public int findPageCount(HeadlineQueryVo headLineQueryVo) {
        List<Object> args = new LinkedList<>();
        String sql = """
                select count(1) from news_headline where is_deleted=0
                """;
        StringBuffer sqlBuffer = new StringBuffer(sql);
        String keywords = headLineQueryVo.getKeyWords();
        if(null != keywords && !"".equals(keywords)){
            sqlBuffer.append(" and title like ? ");
            args.add("%"+keywords+"%");
        }
        Integer type = headLineQueryVo.getType();
        if(null!=type && type != 0){
            sqlBuffer.append(" and type  =  ? ");
            args.add(type);
        }

        Object[] argsArr = args.toArray();
        Long totalSize = baseQueryObject(Long.class, sqlBuffer.toString(), argsArr);
        return totalSize.intValue();
    }


    public int increasePageViews(Integer hid) {
        String sql = "update news_headline set page_views=page_views+1 where hid=?";
        return baseUpdate(sql, hid);
    }


    public HeadlineDetailVo findHeadlineDetail(Integer hid) {  //查询新闻详细信息
        String sql = """
                    select hid,title,article,type, tname typeName ,page_views pageViews,
                    TIMESTAMPDIFF(HOUR,create_time,NOW()) pastHours,publisher,nick_name author
                     from news_headline h left join  news_type t on h.type = t.tid left join news_user u
                     on h.publisher = u.uid where hid = ?
                    """;

        List<HeadlineDetailVo> headlineDetailVoList = baseQuery(HeadlineDetailVo.class, sql, hid);
        if(null != headlineDetailVoList && headlineDetailVoList.size() > 0){
            return headlineDetailVoList.get(0);
        }
        return null;

    }

    public int addNewsHeadline(HeadlineDetailVo headlineDetailVo) {
        String sql = "insert into news_headline values(default,?,?,?,?,0,now(),now(),0)";
        return baseUpdate(sql, headlineDetailVo.getTitle(),
                headlineDetailVo.getArticle(),headlineDetailVo.getType(),
                headlineDetailVo.getPublisher());
    }

    public HeadlineDetailVo getByHid(Integer hid) {
        String sql = "select hid,title,article,type from news_headline where hid=?";
        List<HeadlineDetailVo> headlineDetailVoList = baseQuery(HeadlineDetailVo.class, sql, hid);
        if(null != headlineDetailVoList && headlineDetailVoList.size() > 0){
            return headlineDetailVoList.get(0);
        }
        return null;
    }

    @Override
    public int updateHeadline(HeadlineDetailVo headlineDetailVo) {
        String sql = "update news_headline set title=?,article=?,type=?,update_time=now(),page_views=0 where hid=?";
        return baseUpdate(sql,headlineDetailVo.getTitle(),headlineDetailVo.getArticle(),headlineDetailVo.getType(),headlineDetailVo.getHid());
    }

    @Override
    public int removeByHid(Integer hid) {
        String sql = "update news_headline set is_deleted=1 where hid=?";
        return baseUpdate(sql, hid);
    }
}
