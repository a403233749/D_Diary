package com.vagwork.dao;

import com.vagwork.model.Diary;
import com.vagwork.model.Page;
import com.vagwork.utils.DateHelper;
import com.vagwork.utils.StringHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryDao {
    /**
     * 添加日记
     * @param con
     * @param diary
     * @return
     * @throws Exception
     */
    public int add(Connection con, Diary diary) throws Exception {
        String sql = "insert into t_diary values(null,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,diary.getDiaryTitle());
        pstmt.setString(2,diary.getDiaryContent());
        pstmt.setString(3,DateHelper.formatDate(diary.getDiaryCreateTime()));
        pstmt.setInt(4,diary.getUserId());
        pstmt.setInt(5,diary.getDiaryTypeId());
        return pstmt.executeUpdate();
    }

    /**
     * 更新日记
     * @param con
     * @param diary
     * @return
     */
    public int update(Connection con,Diary diary) throws Exception {
        String sql = "update t_diary set diaryTitle=?, diaryContent=?,diaryTypeId=? where diaryId=? and userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,diary.getDiaryTitle());
        pstmt.setString(2,diary.getDiaryContent());
        pstmt.setInt(3,diary.getDiaryTypeId());
        pstmt.setInt(4,diary.getDiaryId());
        pstmt.setInt(5,diary.getUserId());
        return pstmt.executeUpdate();
    }
    /**
     * 获取记录数
     * @param con
     * @param diary
     * @return
     * @throws Exception
     */
    public int totalCount(Connection con,Diary diary) throws Exception {
        StringBuilder sb = new StringBuilder("select count(diaryId) totalCount from t_diary where userId = ?");
        if(StringHelper.isNotBlank(diary.getDiaryTitle())){
            sb.append(" and diaryTitle like '%"+diary.getDiaryTitle()+"%'");
        }
        PreparedStatement pstmt = null;
        if(diary.getDiaryTypeId() > 0 && null == diary.getDiaryCreateTime()){//根据日记类型查询
            sb.append(" and diaryTypeId = ?");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1,diary.getUserId());
            pstmt.setInt(2,diary.getDiaryTypeId());
        }else if(diary.getDiaryTypeId() == 0 &&  null != diary.getDiaryCreateTime()){//根据日记日期查询
            sb.append(" and DATE_FORMAT(diaryCreateTime,'%Y年%m月%d日') = ?");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1,diary.getUserId());
            pstmt.setString(2,DateHelper.formatDate(diary.getDiaryCreateTime(),"yyyy年MM月dd日"));
        }
        else{
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1,diary.getUserId());
        }
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            return rs.getInt("totalCount");
        }
        return 0;
    }

    /**
     * 查询 根据diaryCreateTime日期分组，并查出日期下的日记数量
     * @param con
     * @param diary
     * @return
     * @throws Exception
     */
    public List<Map<String,String>> getGroupDiaryCreateTime(Connection con,Diary diary) throws Exception {
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();

        String sql = "select DATE_FORMAT(diaryCreateTime,'%Y年%m月%d日') create_time,count(*) totalCount from t_diary where userId=? GROUP BY create_time";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,diary.getUserId());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            String create_time = rs.getString("create_time");
            String totalCount = rs.getString("totalCount");
            Map<String,String> map = new HashMap<String,String>();
            map.put("create_time",create_time);
            map.put("totalCount",totalCount);
            list.add(map);
        }
        return list;
    }

    /**
     * 获取日记列表
     * @param con
     * @param diary
     * @return
     */
    public List<Diary> list(Connection con, Diary diary, Page page) throws Exception {
        List<Diary> diarys = new ArrayList<Diary>();
        StringBuilder sb = new StringBuilder("select * from t_diary where userId=?");
        PreparedStatement pstmt = null;
        if(StringHelper.isNotBlank(diary.getDiaryTitle())){
            sb.append(" and diaryTitle like '%"+diary.getDiaryTitle()+"%'");
        }
        if(diary.getDiaryTypeId() > 0){
            sb.append(" and diaryTypeId="+diary.getDiaryTypeId());
        }
        if(diary.getDiaryCreateTime() != null){
            sb.append(" and DATE_FORMAT(diaryCreateTime,'%Y年%m月%d日') = '"+DateHelper.formatDate(diary.getDiaryCreateTime(),"yyyy年MM月dd日")+"'");
        }
        if(page != null){
            sb.append(" order by diaryCreateTime desc");
            sb.append(" limit ?,?");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1,diary.getUserId());
            pstmt.setInt(2,page.getOffset());
            pstmt.setInt(3,page.getPageSize());
        }else{
            sb.append(" order by diaryCreateTime desc");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1,diary.getUserId());
        }

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            diarys.add(handleResultSet(rs));
            Diary d = new Diary();
        }
        return diarys;
    }

    /**
     * 获取日记 单个
     * @param con
     * @param diary
     * @return
     * @throws Exception
     */
    public Diary getOne(Connection con,Diary diary) throws Exception {
        Diary resultDiary = null;

        String sql = "select * from t_diary where diaryId = ? and userId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,diary.getDiaryId());
        pstmt.setInt(2,diary.getUserId());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            resultDiary = handleResultSet(rs);
        }
        return  resultDiary;
    }


    /**
     * 删除日记
     * @param con
     * @param diary
     * @return
     * @throws Exception
     */
    public int delete(Connection con,Diary diary) throws Exception {
        String sql = "delete from t_diary where diaryId=? and userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,diary.getDiaryId());
        pstmt.setInt(2,diary.getUserId());
        return pstmt.executeUpdate();
    }

    private Diary handleResultSet(ResultSet rs) throws Exception {
        Diary rd = new Diary();
        rd.setDiaryId(rs.getInt("diaryId"));
        rd.setDiaryTitle(rs.getString("diaryTitle"));
        String blob = rs.getString("diaryContent");
        String diaryContent = new String(blob.getBytes("iso-8859-1"),"UTF-8");
        rd.setDiaryContent(diaryContent);
        rd.setDiaryCreateTime(DateHelper.parseString(rs.getString("diaryCreateTime")));
        rd.setUserId(rs.getInt("userId"));
        rd.setDiaryTypeId(rs.getInt("diaryTypeId"));
        return  rd;
    }
}
