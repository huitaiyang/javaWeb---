package dao;

import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlinePageVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadLineDao {
    List<HeadlinePageVo> findPageList(HeadlineQueryVo headLineQueryVo);

    int findPageCount(HeadlineQueryVo headLineQueryVo);

    int increasePageViews(Integer hid);

    HeadlineDetailVo findHeadlineDetail(Integer hid);

    int addNewsHeadline(HeadlineDetailVo headlineDetailVo);

    HeadlineDetailVo getByHid(Integer hid);

    int updateHeadline(HeadlineDetailVo headlineDetailVo);

    int removeByHid(Integer hid);
}
