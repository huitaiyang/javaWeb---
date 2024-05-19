package dao.impl;

import dao.BaseDao;
import dao.NewsUserDao;
import pojo.NewsUser;

import java.util.List;

public class NewsUserDaoImpl extends BaseDao implements NewsUserDao {

    @Override
    public NewsUser findByUserName(String username) { //通过用户名查询用户
        String sql = "select uid,username,user_pwd userPwd,nick_name nickName from news_user where username=?";
        List<NewsUser> newsUserList = baseQuery(NewsUser.class, sql, username);
        if(null!=newsUserList && newsUserList.size()>0){
            return newsUserList.get(0);
        }
        return null;
    }

    @Override
    public NewsUser getUserInfo(Integer uid) { //通过uid获取用户信息
        String sql = "select uid,username,user_pwd userPwd,nick_name nickName from news_user where uid=?";
        List<NewsUser> newsUserList = baseQuery(NewsUser.class, sql, uid);
        if(null!=newsUserList && newsUserList.size()>0){
            return newsUserList.get(0);
        }
        return null;
    }

    @Override
    public void registUser(NewsUser register) {
        String sql = "insert into news_user values(default,?,?,?)";
        baseUpdate(sql, register.getUsername(), register.getUserPwd(), register.getNickName());
    }
}
