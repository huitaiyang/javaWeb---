package service;

import pojo.NewsUser;
import pojo.Vo.HeadlineDetailVo;

public interface NewsUserService {
    NewsUser findByUserName(String username);

    NewsUser findByUserId(Integer uid);

    void registUser(NewsUser register);

}
