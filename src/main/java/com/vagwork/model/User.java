package com.vagwork.model;

public class User {
    private int userId;//用户编号
    private String userName;//用户名
    private String password;//密码
    private String nickName;//昵称
    private String imageName;//头像图片名称
    private String mood;//心情

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String nickName, String imageName, String mood) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.imageName = imageName;
        this.mood = mood;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", mood='" + mood + '\'' +
                '}';
    }
}
