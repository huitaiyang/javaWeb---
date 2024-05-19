package service.impl;

import dao.NewsUserDao;
import dao.impl.NewsUserDaoImpl;
import pojo.NewsUser;
import pojo.Vo.HeadlineDetailVo;
import service.NewsUserService;
import util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {
    private NewsUserDao newsUserDao = new NewsUserDaoImpl();
    @Override
    public NewsUser findByUserName(String username) {
        return newsUserDao.findByUserName(username);
    }

    @Override
    public NewsUser findByUserId(Integer uid) {
        return newsUserDao.getUserInfo(uid);
    }

    @Override
    public void registUser(NewsUser register) {
        // 密码明文转密文
        register.setUserPwd(MD5Util.encrypt(register.getUserPwd()));
        // 存入数据库
        newsUserDao.registUser(register);
    }

}
