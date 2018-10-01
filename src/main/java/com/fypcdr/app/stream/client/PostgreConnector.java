package com.fypcdr.app.stream.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreConnector{

    private static Connection connection;
    private static String globaltablename;

    public static Connection getConnection() throws SQLException {
        String host = "localhost";
        int postgrePort = 5432;
        String database = "cdr_test";
        String username = "postgres";
        String url = "jdbc:postgresql://"+host+":"+postgrePort+"/"+database;
        if(connection == null){
            try {
                connection = DriverManager.getConnection(url,username, "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void createTable(String tablename) throws SQLException {
        globaltablename = tablename;
        Statement stmt = getConnection().createStatement();
        String sql = "CREATE TABLE "+ tablename +
                "(called_num        CHAR(64)," +
                " called_tower      CHAR(6), " +
                " recipient_num     CHAR(64), " +
                " recipient_tower   CHAR(6), " +
                " datetime          CHAR(29)," +
                " duration          CHAR(10))";
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Table created as " + tablename);
    }

    public static String getTableName(){
        return globaltablename;
    }

}
