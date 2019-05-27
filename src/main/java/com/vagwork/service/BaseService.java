package com.vagwork.service;

import com.vagwork.model.Page;
import com.vagwork.utils.DbUtil;
import com.vagwork.utils.PropertiesUtil;

import java.util.List;

/**
 * Service 抽象基类
 * @param <T>
 */
public abstract class BaseService<T> {
    protected PropertiesUtil diaryPu = new PropertiesUtil("diary.properties");
    protected DbUtil dbUtil = new DbUtil(diaryPu);

    /**
     * 保存和更新
     * @param t
     * @return
     */
    public abstract int save(T t);

    /**
     * 删除
     * @param t
     * @return
     */
    public abstract int delete(T t);

    /**
     * 查询数据 单个对象
     * @param t
     * @return
     */
    public abstract T getOne(T t);

    /**
     * 查询数据 多个对象
     * @param t
     * @param page
     * @return
     */
    public abstract List<T> list(T t, Page page);
}
