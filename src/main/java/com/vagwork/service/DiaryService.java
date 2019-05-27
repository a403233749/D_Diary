package com.vagwork.service;

import com.vagwork.dao.DiaryDao;
import com.vagwork.model.Diary;
import com.vagwork.model.Page;
import com.vagwork.utils.DateHelper;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日记Service类
 */
public class DiaryService extends BaseService<Diary> {

    private DiaryDao diaryDao = new DiaryDao();

    /**
     * 添加和更新 日记
     * @param diary
     * @return
     */
    @Override
    public int save(Diary diary) {
        int result = 0;
        Connection con = null;
        try {
             con = dbUtil.getCon();

            int diaryId = diary.getDiaryId();

            if(diaryId > 0){//更新
                result = diaryDao.update(con,diary);
            }else {//添加
                diary.setDiaryCreateTime(new Date());
                result = diaryDao.add(con,diary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 删除日记
     * @param diary
     * @return
     */
    @Override
    public int delete(Diary diary) {
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            result = diaryDao.delete(con,diary);
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
     * 查询 单个diary对象
     * @param diary
     * @return
     */
    @Override
    public Diary getOne(Diary diary) {
        Diary rdiary = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            rdiary = diaryDao.getOne(con,diary);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rdiary;
    }

    /**
     * 查询 多条diary列表
     * @param diary
     * @param page
     * @return
     */
    @Override
    public List<Diary> list(Diary diary, Page page) {
        List list = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            list = diaryDao.list(con,diary,page);
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

    /**
     * 查询 根据diaryCreateTime日期分组，并查出日期下的日记数量
     * @param diary
     * @return
     */
    public List<Map<String,String>> getGroupDiaryCrateTime(Diary diary){
        List list = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            list = diaryDao.getGroupDiaryCreateTime(con,diary);
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

    /**
     * 获取日记总记录数
     * @param diary
     * @return
     */
    public int totalCount(Diary diary){
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            result = diaryDao.totalCount(con,diary);
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
}
