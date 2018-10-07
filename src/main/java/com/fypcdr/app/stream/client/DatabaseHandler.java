package com.fypcdr.app.stream.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Kaushalya
 */
public class DatabaseHandler extends Thread {

    private final int start;
    private final int end;
    private final String[] jsonArray;
    private final ObjectMapper mapper;

    public DatabaseHandler(String[] jsonArray, int start, int end) {
        this.jsonArray = jsonArray;
        this.start = start;
        this.end = end;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run() {

        System.out.println("Starting database insertions. From CDR records " + start + " to " + end);

        String tablename = PostgreConnector.getTableName();
        String insert = "INSERT INTO " + tablename + " (called_num, called_tower, recipient_num, recipient_tower, datetime, duration) VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;
        CDRRecord cdrRecord;
        try {
            ps = PostgreConnector.getConnection().prepareStatement(insert);
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (String item : jsonArray) {
            try {
                cdrRecord = mapper.readValue(item, CDRRecord.class);
                ps.setString(1, cdrRecord.getCalled_num());
                ps.setString(2, cdrRecord.getCalled_tower());
                ps.setString(3, cdrRecord.getRecipient_num());
                ps.setString(4, cdrRecord.getRecipient_tower());
                ps.setString(5, cdrRecord.getDatetime());
                ps.setString(6, cdrRecord.getDuration());
                ps.executeUpdate();
            } 
            catch (IOException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Database insertions finished. From CDR records " + start + " to " + end);
    }
}
