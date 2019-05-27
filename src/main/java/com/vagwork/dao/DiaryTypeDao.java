package com.vagwork.dao;

import com.vagwork.model.DiaryType;
import com.vagwork.utils.StringHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiaryTypeDao {
    /**
     * 添加类别
     * @param con
     * @param diaryType
     * @return
     * @throws Exception
     */
    public int add(Connection con, DiaryType diaryType) throws Exception{
        String sql = "insert into t_diarytype values(null,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,diaryType.getDiaryTypeName());
        pstmt.setInt(2,diaryType.getUserId());
        return pstmt.executeUpdate();
    }

    /**
     * 更新类别
     * @param con
     * @param diaryType
     * @return
     * @throws Exception
     */
     public int update(Connection con,DiaryType diaryType) throws Exception {
         String sql = "update t_diarytype set diaryTypeName=? where diaryTypeId=? and userId=?";
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1,diaryType.getDiaryTypeName());
         pstmt.setInt(2,diaryType.getDiaryTypeId());
         pstmt.setInt(3,diaryType.getUserId());
         return pstmt.executeUpdate();
     }

    /**
     * 删除类别
     * @param con
     * @param diaryType
     * @return
     * @throws Exception
     */
    public int delete(Connection con,DiaryType diaryType) throws Exception {
        String sql = "delete from t_diarytype where diaryTypeId=? and userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,diaryType.getDiaryTypeId());
        pstmt.setInt(2,diaryType.getUserId());
        return pstmt.executeUpdate();
    }

    /**
     * 查询日记类型 列表
     * @param con
     * @param diaryType
     * @return
     * @throws Exception
     */
    public List<DiaryType> list(Connection con,DiaryType diaryType) throws Exception {
        List<DiaryType> list = new ArrayList();
        StringBuilder sql = new StringBuilder("select * from t_diarytype where userId=?");
        PreparedStatement pstmt = null;

        if (StringHelper.isNotBlank(diaryType.getDiaryTypeName())) {
            sql.append(" and diaryTypeName like '%"+diaryType.getDiaryTypeName()+"%'");
        }

        pstmt = con.prepareStatement(sql.toString());
        pstmt.setInt(1,diaryType.getUserId());

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            DiaryType dt = new DiaryType();
            dt.setDiaryTypeId(rs.getInt("diaryTypeId"));
            dt.setDiaryTypeName(rs.getString("diaryTypeName"));
            dt.setUserId(rs.getInt("userId"));
            list.add(dt);
        }
        return list;
    }

    /**
     * 查询日记类型 单个
     * @param con
     * @param diaryType
     * @return
     */
    public DiaryType getOne(Connection con,DiaryType diaryType) throws Exception {
        DiaryType dt = new DiaryType();
        String sql = "select * from t_diarytype where diaryTypeId=? and userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,diaryType.getDiaryTypeId());
        pstmt.setInt(2,diaryType.getUserId());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            dt.setDiaryTypeId(rs.getInt("diaryTypeId"));
            dt.setDiaryTypeName(rs.getString("diaryTypeName"));
            dt.setUserId(rs.getInt("userId"));
        }
        return  dt;
    }
}
