package com.fypcdr.app.stream.client;

import java.sql.*;

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
        connection.setAutoCommit(true);
        return connection;
    }

    public static void createTable(String tablename) throws SQLException {
        globaltablename = tablename;
        Statement statement = getConnection().createStatement();
        if (tableAlreadyExists())
        {
            System.out.println("Table "+tablename+" alredy exists \nFlushing data to receive new records\n");
            String query = "DELETE * FROM "+tablename+";";
            statement.executeUpdate(query);

            System.out.println("Flushing completed ..");
        }
        else
        {

            String sql = "CREATE TABLE " + tablename +
                    "(called_num        CHAR(64)," +
                    " called_tower      CHAR(6), " +
                    " recipient_num     CHAR(64), " +
                    " recipient_tower   CHAR(6), " +
                    " datetime          CHAR(29)," +
                    " duration          CHAR(10))";
            statement.executeUpdate(sql);
            System.out.println("Table created as " + tablename);
        }
        statement.close();
    }

    public static String getTableName(){
        return globaltablename;
    }

    public static boolean tableAlreadyExists() throws SQLException {
        DatabaseMetaData dbm = getConnection().getMetaData();
        // check if table is there
        ResultSet tables = dbm.getTables(null, null, globaltablename, null);
        if (tables.next()) {
            // Table exists
            return true;
        }
        else {
            // Table does not exist
            return false;
        }
    }

}
