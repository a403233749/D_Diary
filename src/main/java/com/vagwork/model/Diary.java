package com.vagwork.model;

import java.util.Date;

public class Diary {
    private int diaryId;
    private String diaryTitle;
    private String diaryContent;
    private Date diaryCreateTime;
    private int userId;
    private int diaryTypeId;

    public Diary() {
    }

    public Diary(String diaryTitle, String diaryContent, Date diaryCreateTime, int userId, int diaryTypeId) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.diaryCreateTime = diaryCreateTime;
        this.userId = userId;
        this.diaryTypeId = diaryTypeId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public Date getDiaryCreateTime() {
        return diaryCreateTime;
    }

    public void setDiaryCreateTime(Date diaryCreateTime) {
        this.diaryCreateTime = diaryCreateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDiaryTypeId() {
        return diaryTypeId;
    }

    public void setDiaryTypeId(int diaryTypeId) {
        this.diaryTypeId = diaryTypeId;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "diaryId=" + diaryId +
                ", diaryTitle='" + diaryTitle + '\'' +
                ", diaryContent='" + diaryContent + '\'' +
                ", diaryCreateTime='" + diaryCreateTime + '\'' +
                ", userId=" + userId +
                ", diaryTypeId=" + diaryTypeId +
                '}';
    }
}
