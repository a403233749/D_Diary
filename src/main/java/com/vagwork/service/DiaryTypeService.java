package com.vagwork.service;
import com.vagwork.dao.DiaryTypeDao;
import com.vagwork.model.DiaryType;
import com.vagwork.model.Page;

import java.sql.Connection;
import java.util.List;

/**
 * 日记类型Service类
 */
public class DiaryTypeService extends BaseService<DiaryType> {
    private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

    /**
     * 添加和更新 日记类型
     * @param diaryType
     * @return
     */
    @Override
    public int save(DiaryType diaryType) {
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if(diaryType.getDiaryTypeId() > 0){//更新
                result = diaryTypeDao.update(con,diaryType);
            }else {//添加
                result = diaryTypeDao.add(con,diaryType);
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
     *删除日记类型
     * @param diaryType
     * @return
     */
    @Override
    public int delete(DiaryType diaryType) {
        int result = 0;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            result = diaryTypeDao.delete(con,diaryType);
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
     * 查询 单个diaryType对象
     * @param diaryType
     * @return
     */
    @Override
    public DiaryType getOne(DiaryType diaryType) {
        DiaryType rdiaryType = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            rdiaryType = diaryTypeDao.getOne(con,diaryType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rdiaryType;
    }

    /**
     * 查询 多条diaryType列表
     * @param diaryType
     * @param page
     * @return
     */
    @Override
    public List<DiaryType> list(DiaryType diaryType, Page page) {
        List list = null;
        Connection con = null;
        try {
            con = dbUtil.getCon();
            list = diaryTypeDao.list(con,diaryType);
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
}
