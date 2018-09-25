package com.fypcdr.app.stream.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Kaushalya
 */
public class DatabaseHandler extends Thread{
    
    private final int start;
    private final int end;
    private String[] jsonArray;
    private final ObjectMapper mapper;
    
    public DatabaseHandler(String[] jsonArray, int start, int end){
        this.jsonArray = jsonArray;
        this.start = start;
        this.end = end;
        this.mapper = new ObjectMapper();
    }
    
    @Override
    public void run() {
        System.out.println("Starting database insertions. From CDR records " + start + " to " + end);
        for (String item : jsonArray) {
            try {
                
                
                CDRRecord cdrRecord = mapper.readValue(item, CDRRecord.class);
                
                //System.out.println(cdrRecord.getCalled_num());
                //Database insertion
                
                
            } catch (IOException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Database insertions finished. From CDR records " + start + " to " + end);
    }
    
}
