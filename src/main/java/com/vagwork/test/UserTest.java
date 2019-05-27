package com.vagwork.test;

import com.vagwork.dao.UserDao;
import com.vagwork.model.User;
import com.vagwork.utils.DbUtil;
import com.vagwork.utils.PropertiesUtil;

import java.sql.Connection;

public class UserTest {
    private static PropertiesUtil pu = new PropertiesUtil("diary.properties");
    private static DbUtil dbUtil = new DbUtil(pu);
    private static UserDao userDao = new UserDao();

    public static void main(String[] args) {
        User user = new User("admin","admin","系统管理员","default.jpg","系统管理员-心情");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int result = userDao.add(con,user);
            if(result > 0){
                System.out.println("注册用户成功");
            }else {
                System.out.println("注册用户失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
