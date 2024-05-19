package service;

import pojo.Vo.HeadlineDetailVo;
import pojo.Vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    Map<String, Object> findPage(HeadlineQueryVo headlineQueryVo);

    HeadlineDetailVo findHeadlineDetail(Integer hid);

    int addNewsHeadline(HeadlineDetailVo headlineDetailVo);

    HeadlineDetailVo getByHid(Integer hid);

    int updateHeadline(HeadlineDetailVo headlineDetailVo);

    int removeByHid(Integer hid);
}
