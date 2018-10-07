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

    public static void main(String[] args) throws SQLException {
    
    public static Properties getProp() {
        return prop;
    }
    
    public Client(){
        Client.prop = new Properties();
    }

    public static void main(String[] args) throws SQLException {
        
        new Client();
        InputStream input;
        try {
            input = new FileInputStream("conf.properties");
            // load properties file
            prop.load(input);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String tablename = "cdrtesttable";
        PostgreConnector.createTable(tablename);
        StreamHandler streamHandler = new StreamHandler();
        int noOfRecords = Integer.parseInt(args[0]);
  
        Long x = System.currentTimeMillis();

        streamHandler.requestCDRRecords(noOfRecords);

        Long y = System.currentTimeMillis();
        System.out.println((y-x)/1000 + " Seconds taken.");
    }


}
