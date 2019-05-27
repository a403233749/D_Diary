package com.vagwork.model;

public class DiaryType {
    private int diaryTypeId;//日志类别编号
    private String diaryTypeName;//日志类别名称
    private int userId;//用户编号
    private int diaryCount;//日记类别下的日记数量
    public DiaryType() {
    }

    public DiaryType(String diaryTypeName, int userId) {
        this.diaryTypeName = diaryTypeName;
        this.userId = userId;
    }

    public int getDiaryTypeId() {
        return diaryTypeId;
    }

    public void setDiaryTypeId(int diaryTypeId) {
        this.diaryTypeId = diaryTypeId;
    }

    public String getDiaryTypeName() {
        return diaryTypeName;
    }

    public void setDiaryTypeName(String diaryTypeName) {
        this.diaryTypeName = diaryTypeName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }

    @Override
    public String toString() {
        return "DiaryType{" +
                "diaryTypeId=" + diaryTypeId +
                ", diaryTypeName='" + diaryTypeName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
