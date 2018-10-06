package com.fypcdr.app.stream.client;

import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author Lahiru Kaushalya
 */
public class Client {

    public static void main(String[] args) throws SQLException {
        Long x = System.currentTimeMillis();

        int noOfRecords = Integer.parseInt(args[0]);
        String tablename = "cdrtesttable";
        PostgreConnector.createTable(tablename);
        StreamHandler streamHandler = new StreamHandler();
        streamHandler.requestCDRRecords(noOfRecords);

        Long y = System.currentTimeMillis();
        System.out.println((y-x)/1000 + " Seconds taken.");
    }


}
