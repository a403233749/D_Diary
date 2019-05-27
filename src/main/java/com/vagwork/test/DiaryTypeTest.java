package com.vagwork.test;

import com.vagwork.dao.DiaryTypeDao;
import com.vagwork.model.DiaryType;
import com.vagwork.utils.DbUtil;
import com.vagwork.utils.PropertiesUtil;

import java.sql.Connection;
import java.util.List;

public class DiaryTypeTest {
    private static PropertiesUtil pu = new PropertiesUtil("diary.properties");
    private static DbUtil dbUtil = new DbUtil(pu);
    private static DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

    public static void main(String[] args) {
        DiaryType dt1 = new DiaryType("人生感悟",1);
        DiaryType dt2 = new DiaryType("工作类",1);
        DiaryType dt3 = new DiaryType("学习类",1);
        DiaryType dt4 = new DiaryType("生活类",1);

        Connection con = null;
        try {
            con = dbUtil.getCon();
//            int result = diaryTypeDao.add(con,dt1);
//            result = diaryTypeDao.add(con,dt2);
//            result = diaryTypeDao.add(con,dt3);
//            result = diaryTypeDao.add(con,dt4);

//            dt1.setDiaryTypeId(1);
//            dt1.setDiaryTypeName("人生感悟(改)");
//            int result = diaryTypeDao.update(con,dt1);

//            dt1.setDiaryTypeId(1);
//            int result = diaryTypeDao.delete(con,dt1);

//            if(result > 0){
//                System.out.println("成功");
//            }else {
//                System.out.println("失败");
//            }

            DiaryType dt_c = new DiaryType();
            dt_c.setDiaryTypeName("学习");
            dt_c.setUserId(1);
            List<DiaryType> list = diaryTypeDao.list(con,dt_c);
            System.out.println(list);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
