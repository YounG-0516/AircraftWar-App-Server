package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class MyDataBase {

    public static void main(String[] args) {
        createDataBase();
        createUser();
    }
    public static void createDataBase() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:demo.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Create database successfully");
    }

    /**
     * 创建数据库表
     */
    public static void createUser(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:demo.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USER " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME           TEXT    NOT NULL, " +
                    " PASSWORD        TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    /**
     * 往数据库的表中插入数据
     */
    public static void createNewAccount(String name,String password){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:demo.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO USER (NAME,PASSWORD) " +
                    "VALUES ('"+
                    name + "','" +
                    password + "');";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Account created successfully");
    }

    /**
     * 获取所有用户信息
     */
    public static HashMap<String,String> getAllUser(){
        Connection c = null;
        Statement stmt = null;
        HashMap<String,String> map = new HashMap<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:demo.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
            while(rs.next()){
                map.put( rs.getString("name"),rs.getString("password"));
            }
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Get users successfully");
        return map;
    }

}
