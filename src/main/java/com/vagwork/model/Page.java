package com.vagwork.model;

public class Page {
    private int page;//当前页
    private int offset;//查询数据开始
    private int pageSize;//查询数据条数
    private int totalCount;//数据总条数
    private int totalPage;//数据总页数

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        offset = (page - 1) * pageSize;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        totalPage = totalCount / pageSize;
        if(totalCount % pageSize != 0){
            totalPage += 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
