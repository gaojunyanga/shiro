package com.glls.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * eclipse常用快捷键
 * 导包:ctrl+shift+o
 * 复制选择行或当前行到下一行:ctrl+alt+下键(方向键)
 * 捕获异常:alt+shift+z
 * 格式化代码:ctrl+shift+f
 * 注释或取消注释:ctrl+shift+c
 * 删除当前行:ctrl+d
 */
public class JdbcUtil {
    // 连接数据库的驱动，url，用户名和密码
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASWORD = "123456";

    private static Connection conn;// 连接
    private static PreparedStatement psmt;// PreparedStatement对象
    private static ResultSet rs;// 结果集

    /**
     * 获取连接的方法
     */
    public static void getConn() {
        try {
            Class.forName(DRIVER);// 加载驱动
            conn = DriverManager.getConnection(URL, USER, PASWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 释放资源
     */
    public static void closeAll(){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(psmt!=null){
                psmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 封装查询操作
     * @param sql:select * from userinfo where userName=? and userPass=?
     * @param params {"zhangsan","123"}
     * 为占位符绑定值(占位符的下标从1开始,数组的下标从0开始并且都是连续的)，可以通过循环为占位符绑定值
     * for(int i=0;i<params.length;i++){
     *    psmt.setObject(i+1,params[i]);//i=0;psmt.setObject(1,params[0]); i=1;psmt.setObject(2,params[1])
     * }
     * @return
     */
    public static ResultSet executeQuery(String sql,Object...params){
        //获取连接
        getConn();
        try {
            psmt = conn.prepareStatement(sql);//创建PreparedStatement对象
            if(params!=null&&params.length>0){//为占位符绑定值
                for(int i=0;i<params.length;i++){
                    psmt.setObject(i+1, params[i]);
                }
            }
            rs = psmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 封装增，删，改的操作
     * @param sql
     * @param params
     * @return 受影响的行数
     */
    public static int executeUpdate(String sql,Object...params){
        int count=0;//受影响的行数
        //获取连接
        getConn();
        try {
            psmt = conn.prepareStatement(sql);//创建PreparedStatement对象
            if(params!=null&&params.length>0){//为占位符绑定值
                for(int i=0;i<params.length;i++){
                    psmt.setObject(i+1, params[i]);
                }
            }
            count = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeAll();//释放资源
        }
        return count;
    }

}
