package dao;

import pojo.NewsUser;

public interface NewsUserDao {
    NewsUser findByUserName(String username);

    NewsUser getUserInfo(Integer uid);

    void registUser(NewsUser register);
}
