package com.vagwork.service;

import com.vagwork.dao.UserDao;
import com.vagwork.model.Page;
import com.vagwork.model.User;
import com.vagwork.utils.StringHelper;

import java.sql.Connection;
import java.util.List;

public class UserService extends BaseService<User> {
    private UserDao userDao = new UserDao();

    /**
     * 保存和更新
     * @param user
     * @return
     */
    @Override
    public int save(User user) {
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();

            if(user.getUserId() > 0){//更新
                result = userDao.update(con,user);
            }else {//保存
                result = userDao.add(con,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @Override
    public int delete(User user) {
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();

            if(user.getUserId() > 0){
                result = userDao.delete(con,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询 单个user对象
     * @param user
     * @return
     */
    @Override
    public User getOne(User user) {
        User rUser = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if(user.getUserId() > 0){
                rUser = userDao.getOne(con,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rUser;
    }

    /**
     * 查询 多条user对象
     * @param user
     * @param page
     * @return
     */
    @Override
    public List<User> list(User user, Page page) {
        List list = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            list = userDao.list(con,user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public User login(User user){
        User rUser = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if(StringHelper.isNotBlank(user.getUserName()) && StringHelper.isNotBlank(user.getPassword())){
                rUser = userDao.login(con,user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rUser;
    }
}
