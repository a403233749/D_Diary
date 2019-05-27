package com.vagwork.dao;

import com.vagwork.model.User;
import com.vagwork.utils.MD5Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    /**
     * 登录
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection con,User user) throws Exception {
        User resultUser = null;
        String sql = "select * from t_user where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2, MD5Util.md5(user.getPassword()));
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            resultUser = handleUser(rs);
        }
        return  resultUser;
    }

    /**
     * 查询 单个user对象
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public User getOne(Connection con,User user) throws Exception {
        User resultUser = null;
        String sql = "select * from t_user where userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getUserId());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            resultUser = handleUser(rs);
        }
        return resultUser;
    }

    /**
     * 查询 多个user对象
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public List<User> list(Connection con,User user) throws Exception {
        List<User> list = new ArrayList<User>();
        String sql = "select * from t_user";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            User rUser = handleUser(rs);
            list.add(rUser);
        }
        return list;
    }

    /**
     * 添加用户
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int add(Connection con,User user) throws Exception{
        String sql = "insert into t_user values(null,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getUserName());
        pstmt.setString(2,MD5Util.md5(user.getPassword()));
        pstmt.setString(3,user.getNickName());
        pstmt.setString(4,user.getImageName());
        pstmt.setString(5,user.getMood());
        return pstmt.executeUpdate();
    }

    /**
     * 更新用户
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int update(Connection con,User user) throws Exception{
        String sql = "update t_user set nickName=?,imageName=?,mood=? where userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getNickName());
        pstmt.setString(2,user.getImageName());
        pstmt.setString(3,user.getMood());
        pstmt.setInt(4,user.getUserId());
        return pstmt.executeUpdate();
    }

    /**
     * 更新用户密码
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int updatePwd(Connection con,User user) throws Exception {
        String sql = "update t_user set password=? where userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPassword());
        pstmt.setInt(2,user.getUserId());
        return pstmt.executeUpdate();
    }
    /**
     * 删除用户
     * @param con
     * @param user
     * @return
     */
    public int delete(Connection con,User user) throws Exception {
        String sql = "delete from t_user where userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,user.getUserId());
        return  pstmt.executeUpdate();
    }


    private User handleUser(ResultSet rs) throws Exception {
        User resultUser = new User();
        resultUser.setUserId(rs.getInt("userId"));
        resultUser.setUserName(rs.getString("userName"));
        resultUser.setNickName(rs.getString("nickName"));
        resultUser.setImageName(rs.getString("imageName"));
        resultUser.setMood(rs.getString("mood"));
        return resultUser;
    }
}
