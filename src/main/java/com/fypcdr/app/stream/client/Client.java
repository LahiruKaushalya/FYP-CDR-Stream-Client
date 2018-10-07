package com.fypcdr.app.stream.client;

import akka.actor.ActorSystem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Kaushalya
 */
public class Client {
    
    private static Properties prop;
    
    public static Properties getProp() {
        return prop;
    }
    
    public Client(){
        Client.prop = new Properties();
    }

    public static void main(String[] args) throws SQLException {
        
        new Client();
        
        try {
            // load properties file
            InputStream input = new FileInputStream("conf.properties");
            prop.load(input);
        }
        catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String tablename = "cdrtesttable";
        PostgreConnector.createTable(tablename);
        
        StreamHandler streamHandler = new StreamHandler();
        int noOfRecords = Integer.parseInt(args[0]);
        
        streamHandler.requestCDRRecords(noOfRecords);
    }
}
