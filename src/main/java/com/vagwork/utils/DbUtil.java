package com.vagwork.utils;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbUtil {
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;
    private String jdbcName;

    public DbUtil(PropertiesUtil pu) {
        dbUrl = pu.readProperty("dbUrl");
        dbUserName = pu.readProperty("dbUserName");
        dbPassword = pu.readProperty("dbPassword");
        jdbcName = pu.readProperty("jdbcName");
    }

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        return con;
    }

    /**
     * 关闭数据库连接
     * @param con
     * @throws Exception
     */
    public void closeCon(Connection con) throws Exception{
        if(con!=null){
            con.close();
        }
    }

    public static void main(String[] args) {
        PropertiesUtil pu = new PropertiesUtil("diary.properties");
        DbUtil dbUtil=new DbUtil(pu);
        try {
            dbUtil.getCon();
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
