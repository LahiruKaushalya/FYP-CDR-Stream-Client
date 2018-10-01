package com.fypcdr.app.stream.client;

import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author Lahiru Kaushalya
 */
public class Client {

    public static void main(String[] args) throws SQLException {

        int noOfRecords = Integer.parseInt(args[0]);
        String tablename = "CDR_test_3";
        PostgreConnector.createTable(tablename);

        StreamHandler streamHandler = new StreamHandler();

        streamHandler.requestCDRRecords(noOfRecords);
    }

}
